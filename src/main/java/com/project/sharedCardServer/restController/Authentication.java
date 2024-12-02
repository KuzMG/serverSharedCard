package com.project.sharedCardServer.restController;

import com.project.sharedCardServer.email.CodeSender;
import com.project.sharedCardServer.model.category.Category;
import com.project.sharedCardServer.model.category.CategoryDao;
import com.project.sharedCardServer.model.check.CheckDao;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.currency.CurrencyDao;
import com.project.sharedCardServer.model.group.GroupDao;
import com.project.sharedCardServer.model.group_users.GroupUsers;
import com.project.sharedCardServer.model.group_users.GroupUsersDao;
import com.project.sharedCardServer.model.metrics.Metric;
import com.project.sharedCardServer.model.metrics.MetricDao;
import com.project.sharedCardServer.model.product.Product;
import com.project.sharedCardServer.model.product.ProductDao;
import com.project.sharedCardServer.model.recipe.Recipe;
import com.project.sharedCardServer.model.recipe.RecipeDao;
import com.project.sharedCardServer.model.recipe_product.RecipeProduct;
import com.project.sharedCardServer.model.recipe_product.RecipeProductDao;
import com.project.sharedCardServer.model.shop.Shop;
import com.project.sharedCardServer.model.shop.ShopDao;
import com.project.sharedCardServer.model.target.TargetDao;
import com.project.sharedCardServer.model.user.UserAccount;
import com.project.sharedCardServer.model.user.UserDao;
import com.project.sharedCardServer.restController.dto.AccountResponse;
import com.project.sharedCardServer.restController.dto.AuthResponse;
import com.project.sharedCardServer.restController.dto.DictionaryResponse;
import com.project.sharedCardServer.restController.dto.RegistrationBody;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.DoubleAccumulator;

@RestController
public class Authentication {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupUsersDao groupUsersDao;
    @Autowired

    private MetricDao metricDao;
    @Autowired

    private ProductDao productDao;
    @Autowired
    private RecipeDao recipeDao;
    @Autowired
    private RecipeProductDao recipeProductDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private CheckDao checkDao;
    @Autowired
    private TargetDao targetDao;
    public static final String HEADER_ID_USER = "id-user";
    public static final String HEADER_PASSWORD_USER = "password-user";

    private DoubleAccumulator metricAuthentication;
    private DoubleAccumulator metricRegistration;
    private DoubleAccumulator metricVerification;
    @Autowired
    private MeterRegistry metricRegistry;
    @PostConstruct
    private void init(){
        metricAuthentication = new DoubleAccumulator((x,y) -> y,0.0d);
        metricRegistration = new DoubleAccumulator((x,y) -> y,0.0d);
        metricVerification = new DoubleAccumulator((x,y) -> y,0.0d);
        metricRegistry.gauge("metric_authentication",metricAuthentication);
        metricRegistry.gauge("metric_registration",metricAuthentication);
        metricRegistry.gauge("metric_verification",metricAuthentication);
    }
    @Timed(value ="auth.timed.gg")
    @GetMapping("/authentication")
    public ResponseEntity<AuthResponse> authentication(@RequestParam("login") String login, @RequestParam("password") String password) {
        if (userDao.authentication(login, password)) {
            UUID userId = userDao.getUserAccount(login).getId();
            UUID groupId = groupDao.getDefaultGroup(login);
            metricAuthentication.accumulate(metricAuthentication.get()+1);
            return new ResponseEntity<>(new AuthResponse(userId, groupId), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Timed("registr")
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationBody body) {
        String email = body.getEmail();
        String password = body.getPassword();
        String name = body.getName();
        Date date = new Date(body.getDate());
        Boolean gender = body.getGender();
        Double weight = body.getWeight();
        Integer height = body.getHeight();
        if (!userDao.isExist(email)) {
            UUID userId = userDao.createUser(email, password,name,date,gender,height,weight);
            String userPic = fileManager.saveDefaultUserPic(userId);
            userDao.updatePic(userId, userPic);

            UUID groupId = groupDao.createDefaultGroup();
            String groupPic = fileManager.saveDefaultGroupPic(userId);
            groupDao.updatePic(groupId, groupPic);

            groupUsersDao.create(userId, groupId, GroupUsers.CREATOR);
        }
        if (userDao.isVerified(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Аккаунт уже существует!");

        UserAccount userAccount = userDao.getUserAccount(email);
        int countCode = userAccount.getCountCode();
        Date dateCode = userAccount.getDateCode();

        Date dateNow = new Date();
        if (dateCode == null)
            dateCode = new Date();
        long dateDifference = dateNow.getTime() - dateCode.getTime();

        if (dateDifference < 5 * 60 * 1000) {
            if (countCode < 3) {
                String code = CodeSender.generateCode();
                Thread thread = new Thread(() -> CodeSender.send(email, code));
                thread.start();
                userDao.saveCode(email, code, dateNow, ++countCode);
                metricRegistration.accumulate(metricRegistration.get() +1);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Превышено количетсво отправленных сообщений!");
            }
        } else {
            String code = CodeSender.generateCode();
            Thread thread = new Thread(() -> CodeSender.send(email, code));
            thread.start();
            userDao.saveCode(email, code, dateNow, 1);
            metricRegistration.accumulate(metricRegistration.get() +1);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/verification")
    public ResponseEntity<AuthResponse> verification(@RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("code") String code) {
        if (userDao.checkPassword(login, password) && userDao.checkCode(login, code) && !userDao.isVerified(login)) {
            userDao.verification(login);
            UUID idUser = userDao.getUserAccount(login).getId();
            UUID idGroup = groupDao.getDefaultGroup(login);
            metricVerification.accumulate(metricVerification.get() +1);
            return new ResponseEntity<>(new AuthResponse(idUser, idGroup), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/dictionary")
    public ResponseEntity<DictionaryResponse> getDictionary(@RequestHeader HttpHeaders headers) {
        UUID idUser = UUID.fromString(headers.getFirst(HEADER_ID_USER));
        String passwordUser = headers.getFirst(HEADER_PASSWORD_USER);
        if (!userDao.checkPassword(idUser, passwordUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Category> categories = categoryDao.getAll();
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
        UUID idUser = UUID.fromString(headers.getFirst(HEADER_ID_USER));
        String passwordUser = headers.getFirst(HEADER_PASSWORD_USER);
        if (!userDao.checkPassword(idUser, passwordUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        AccountResponse accountResponse = new AccountResponse(
                userDao.getUsers(idUser),
                groupDao.getAll(idUser,true),
                groupUsersDao.getAll(idUser,true),
                checkDao.getAll(idUser),
                targetDao.getAll(idUser)
        );
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }


}
