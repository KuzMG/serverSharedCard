package com.project.sharedCardServer.restController;

import com.project.sharedCardServer.email.CodeSender;
import com.project.sharedCardServer.model.basket.BasketDao;
import com.project.sharedCardServer.model.category_product.CategoryProduct;
import com.project.sharedCardServer.model.category_product.CategoryProductDao;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.currency.CurrencyDao;
import com.project.sharedCardServer.model.group.GroupDao;
import com.project.sharedCardServer.model.group_persons.GroupPersons;
import com.project.sharedCardServer.model.group_persons.GroupPersonsDao;
import com.project.sharedCardServer.model.history.HistoryDao;
import com.project.sharedCardServer.model.metric.Metric;
import com.project.sharedCardServer.model.metric.MetricDao;
import com.project.sharedCardServer.model.person.PersonAccount;
import com.project.sharedCardServer.model.person.PersonDao;
import com.project.sharedCardServer.model.product.Product;
import com.project.sharedCardServer.model.product.ProductDao;
import com.project.sharedCardServer.model.purchase.PurchaseDao;
import com.project.sharedCardServer.model.recipe.Recipe;
import com.project.sharedCardServer.model.recipe.RecipeDao;
import com.project.sharedCardServer.model.recipe_product.RecipeProduct;
import com.project.sharedCardServer.model.recipe_product.RecipeProductDao;
import com.project.sharedCardServer.model.shop.Shop;
import com.project.sharedCardServer.model.shop.ShopDao;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import com.project.sharedCardServer.restController.dto.AuthResponse;
import com.project.sharedCardServer.restController.dto.DictionaryResponse;
import com.project.sharedCardServer.restController.dto.RegistrationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class Authentication {
    @Autowired
    private PersonDao personDao;
    @Autowired
    private CategoryProductDao categoryProductDao;
    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupPersonsDao groupPersonsDao;
    @Autowired
    private MetricDao metricDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private RecipeProductDao recipeProductDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private HistoryDao historyDao;
    public static final String HEADER_ID_PERSON = "id-person";
    public static final String HEADER_PASSWORD = "password-person";


    @GetMapping("/authentication")
    public ResponseEntity<AuthResponse> authentication(@RequestParam("login") String login, @RequestParam("password") String password) {
        if (personDao.authentication(login, password)) {
            UUID userId = personDao.getPersonAccount(login).getId();
            UUID groupId = groupDao.getDefaultGroup(login);
            return new ResponseEntity<>(new AuthResponse(userId, groupId), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationBody body) {
        String email = body.getEmail();
        String password = body.getPassword();
        String name = body.getName();
        Date date = new Date(body.getDate());
        Boolean gender = body.getGender();
        if (!personDao.isExist(email)) {
            UUID personId = personDao.createPerson(email, password, name, date, gender);
            String personPic = FileManager.saveDefaultPersonPic(personId);
            personDao.updatePic(personId, personPic);

            UUID groupId = groupDao.createDefaultGroup();
            String groupPic = FileManager.saveDefaultGroupPic(personId);
            groupDao.updatePic(groupId, groupPic);

            groupPersonsDao.create(personId, groupId, GroupPersons.CREATOR);
        }
        if (personDao.isVerified(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Аккаунт уже существует!");

        PersonAccount personAccount = personDao.getPersonAccount(email);
        int countCode = personAccount.getCountCode();
        Date dateCode = personAccount.getDateCode();

        Date dateNow = new Date();
        if (dateCode == null)
            dateCode = new Date();
        long dateDifference = dateNow.getTime() - dateCode.getTime();

        if (dateDifference < 5 * 60 * 1000) {
            if (countCode < 3) {
                String code = CodeSender.generateCode();
                Thread thread = new Thread(() -> CodeSender.send(email, code));
                thread.start();
                personDao.saveCode(email, code, dateNow, ++countCode);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Превышено количетсво отправленных сообщений!");
            }
        } else {
            String code = CodeSender.generateCode();
            Thread thread = new Thread(() -> CodeSender.send(email, code));
            thread.start();
            personDao.saveCode(email, code, dateNow, 1);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/verification")
    public ResponseEntity<AuthResponse> verification(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("code") String code) {
        if (personDao.checkPassword(login, password) && personDao.checkCode(login, code) && !personDao.isVerified(login)) {
            personDao.verification(login);
            UUID personId = personDao.getPersonAccount(login).getId();
            UUID groupId = groupDao.getDefaultGroup(login);
            return new ResponseEntity<>(new AuthResponse(personId, groupId), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/dictionary")
    public ResponseEntity<DictionaryResponse> getDictionary(@RequestHeader HttpHeaders headers) {
        UUID personId = UUID.fromString(headers.getFirst(HEADER_ID_PERSON));
        String password = headers.getFirst(HEADER_PASSWORD);
        if (!personDao.checkPassword(personId, password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<CategoryProduct> categories = categoryProductDao.getAll();
        List<Currency> currencies = currencyDao.getAll();
        List<Metric> metrics = metricDao.getAll();
        List<Product> products = productDao.getAll();
        List<Recipe> recipes = recipeDao.getAll();
        List<RecipeProduct> recipeProducts = recipeProductDao.getAll();
        List<Shop> shops = shopDao.getAll();
        return new ResponseEntity<>(new DictionaryResponse(categories, currencies, metrics, products, recipes, recipeProducts, shops), HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<AccountResponse> getАccount(@RequestHeader HttpHeaders headers) {
        UUID personId = UUID.fromString(headers.getFirst(HEADER_ID_PERSON));
        String passwordUser = headers.getFirst(HEADER_PASSWORD);
        if (!personDao.checkPassword(personId, passwordUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        AccountResponse accountResponse = new AccountResponse(
                personDao.getPersons(personId),
                groupDao.getAll(personId, true),
                groupPersonsDao.getAll(personId, true),
                purchaseDao.getAll(personId),
                basketDao.getAll(personId),
                historyDao.getAll(personId)
        );
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }


}
