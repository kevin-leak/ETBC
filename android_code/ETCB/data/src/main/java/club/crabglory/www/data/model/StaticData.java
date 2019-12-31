package club.crabglory.www.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import club.crabglory.www.common.Application;
import club.crabglory.www.data.R;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.data.model.db.Micro;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.persistence.Account;

public class StaticData {
    private static final String KEY_INIT_STATIC_DATA = "KEY_INIT_STATIC_DATA";
    private static final String BASE_URL = "https://img9.doubanio.com/view/subject/s/public/";
    private static final String KEY = "?apikey=0df993c66c0c636e29ecbb5344252a4a";

    public static void getBook(Application app) {
        SharedPreferences sp = app.getSharedPreferences(StaticData.class.getName(), Context.MODE_PRIVATE);
        boolean isLoad = sp.getBoolean(KEY_INIT_STATIC_DATA, false);
        if (!isLoad) {
            initBook();
            sp.edit().putBoolean(KEY_INIT_STATIC_DATA, true).apply();
        }

    }

    private static void initBook() {
        User user = new User();
        user.setAvatar("https://avatars1.githubusercontent.com/u/30002212?s=460&v=4");
        user.setId(UUID.randomUUID().toString());
        user.setPhone("18870742136");
        user.setAddress("江西理工大学");
        user.setModifyAt(new Date());
        user.setSex(0);
        user.setName("kevin");
        user.save();
        List<Book> bookList = new ArrayList<>();


        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setName("Jane Eyre");
        book.setAuthor("Jerome");
        book.setImage(BASE_URL + "s5924326.jpg" + KEY);
        book.setCount(10);
        book.setDescription("Jane Eyre (Mia Wasikowska), who lost her parents from a young age, " +
                "lived in her aunt's house and was deceived and aunted by her cousin (Craig Roberts).");
        book.setPrice(26);
        book.setSales(0);
        book.setType(1);
        book.setUpper(user);
        book.setUpTime(new Date());
        book.setModifyAt(new Date());
        bookList.add(book);

        Book book1 = new Book();
        book1.setId(UUID.randomUUID().toString());
        book1.setName("Republic");
        book1.setAuthor("Plato");
        book1.setImage("https://img3.doubanio.com/view/subject/l/public/s4037761.jpg");
        book1.setCount(2);
        book1.setDescription("Plato (427-347 BC) was a great philosopher of ancient Greece, " +
                "a student of Socrates (469-399 BC), and a teacher of Aristotle (384-322 BC). " +
                "He spent most of his life in Athens, the center of the ancient Greek national " +
                "culture. He loves the motherland and loves philosophy. His highest ideal, " +
                "philosophers should be politicians, politicians should be philosophers. " +
                "The philosopher is not a stalker hiding in the ivory tower. He should apply " +
                "what he has learned and practice. A person with a philosophical mind must " +
                "have a political power and a political person with a philosophical mind.");
        book1.setPrice(25);
        book1.setSales(0);
        book1.setType(2);
        book1.setUpper(user);
        book1.setUpTime(new Date());
        book1.setModifyAt(new Date());
        bookList.add(book1);


        Book book2 = new Book();
        book2.setId(UUID.randomUUID().toString());
        book2.setImage("https://img3.doubanio.com/view/subject/l/public/s1441092.jpg");
        book2.setName("Effective Manage");
        book2.setAuthor("Peter F. Drucker");
        book2.setPrice(45);
        book2.setSales(2);
        book2.setCount(10);
        book2.setType(3);
        book2.setUpper(user);
        book2.setUpTime(new Date());
        book2.setModifyAt(new Date());
        book2.setDescription("Peter F. Drucker was considered one of management's top thinkers. " +
                "As the author of more than 35 books, " +
                "his ideas have had an enormous impact on shaping the modern corporation.\n" +
                "In 2002, he was awarded the Presidential Medal of Freedom. During his lifetime, " +
                "Drucker was a writer, teacher, philosopher, reporter, consultant, and professor " +
                "at the Peter F.");
        bookList.add(book2);

        Book book3 = new Book();
        book3.setId(UUID.randomUUID().toString());
        book3.setImage("https://img9.doubanio.com/view/subject/l/public/s12824834.jpg");
        book3.setName("Psychoanalysis");
        book3.setAuthor("Clara Thompson");
        book3.setPrice(65);
        book3.setSales(2);
        book3.setCount(12);
        book3.setType(0);
        book3.setUpper(user);
        book3.setUpTime(new Date());
        book3.setModifyAt(new Date());
        book3.setDescription("Clara Thompson was a leading representative of the cultural interpersonal " +
                "school of psychoanalysis, sometimes known as the \"neo-Freudians, \" " +
                "which included Karen Horney, Erich Fromm, and Harry Stack Sullivan. " +
                "\"Classical analysts\" once viewed neo-Freudians with the greatest suspicion and mistrust");
        bookList.add(book3);

        Book book4 = new Book();
        book4.setId(UUID.randomUUID().toString());
        book4.setImage("https://img3.doubanio.com/view/subject/l/public/s3326985.jpg");
        book4.setName("Pride and Prejudice");
        book4.setAuthor("Jane Austen");
        book4.setPrice(45);
        book4.setSales(2);
        book4.setCount(10);
        book4.setType(1);
        book4.setUpper(user);
        book4.setUpTime(new Date());
        book4.setModifyAt(new Date());
        book4.setDescription("This bookModelView describes the arrogant single young Darcy and the " +
                "second lady of the prejudice, Elizabeth, the wealthy single noble Bingley " +
                "and the singer's great lady Ji Ying, fully expressing the author's own view " +
                "of marriage, paying attention to economic interests to love and The novel has" +
                " been adapted into films and TV series many times. The impact of marriage." +
                " The plot of the novel is full of comics, humorous language, " +
                "and is in Austin's novels.");
        bookList.add(book4);

        Book book5 = new Book();
        book5.setId(UUID.randomUUID().toString());
        book5.setImage("https://img3.doubanio.com/view/subject/l/public/s2352093.jpg");
        book5.setName("Harry Potter");
        book5.setAuthor("J. K. Rowling");
        book5.setPrice(25);
        book5.setSales(2);
        book5.setCount(10);
        book5.setType(3);
        book5.setUpper(user);
        book5.setUpTime(new Date());
        book5.setModifyAt(new Date());
        book5.setDescription("J. K. Rowling (1965- ), a British female writer who loved writing " +
                "since childhood and worked as a teacher and secretary for a short time. " +
                "At the age of twenty-four, she was born with the idea of creating a series " +
                "of \"Harry Potter\" novels on her train journey to London. Seven years later, " +
                "\"Harry Potter and the Sorcerer's Stone\" came out, after which she successively" +
                " created \"Harry Potter and the Chamber of Secrets\", " +
                "\"Harry Potter and the Prisoner of Azkaban\", \"Harry Potter and Flame Cup");
        bookList.add(book5);

//        Book book6 = new Book();
//        book6.setId(UUID.randomUUID().toString());
//        book6.setImage("https://img9.doubanio.com/view/subject/l/public/s8912644.jpg");
//        book6.setName("Bible");
//        book6.setAuthor("Christian");
//        book6.setPrice(15);
//        book6.setSales(2);
//        book6.setCount(10);
//        book6.setType(3);
//        book6.setUpper(getUser());
//        book6.setUpTime(new Date());
//        book6.setModifyAt(new Date());
//        book6.setDescription("Didn't we say to you in Egypt, 'Leave us alone; let us " +
//                "serve the Egyptians'? It would have been better for us to serve the " +
//                "Egyptians than to die in the desert!" );
//        bookList.add(book6);

        Book book7 = new Book();
        book7.setId(UUID.randomUUID().toString());
        book7.setImage("https://img1.doubanio.com/view/subject/l/public/s27172838.jpg");
        book7.setName("Learn More Study Less");
        book7.setAuthor("Scott Young");
        book7.setPrice(105);
        book7.setSales(2);
        book7.setCount(10);
        book7.setType(3);
        book7.setUpper(user);
        book7.setUpTime(new Date());
        book7.setModifyAt(new Date());
        book7.setDescription("Smart people don’t just think better, they think differently. " +
                "This e-book explores the process I used to ace my finals with little studying. " +
                "It isn’t a quick fix, but it provides a path for other people who want to take" +
                " control of the learning process. The full-version" +
                " includes a 228 page e-book and six bonus printouts designed to help you master " +
                "these techniques");

        Goods goods = book7.toCarGoods(1);
        goods.setState(true);
        goods.save();
        bookList.add(book7);

        DbHelper.save(Book.class, bookList.toArray(new Book[0]));
    }

    // just for test
    public static User getUser() {
        User user = new User();
        user.setAvatar("https://raw.githubusercontent.com/kevin-leak/CattleIM/master/IMSoftData/UIDesign/app/ic_launcher.png");
        user.setId(UUID.randomUUID().toString());
        user.setName("d_kevin");
        user.setPhone("18870" + new Random().nextInt() + "42138");
        user.setSex(0);
        user.setAddress("江西理工大学 红旗大道");
        user.setFavorite(1.5f);
        user.save();
        return user;
    }


    public static void initVideo() {
        int[] imgs = {R.mipmap.img_video_1, R.mipmap.img_video_2, R.mipmap.img_video_3, R.mipmap.img_video_4, R.mipmap.img_video_5, R.mipmap.img_video_6, R.mipmap.img_video_7, R.mipmap.img_video_8};
        int[] videos = {R.raw.video_1, R.raw.video_2, R.raw.video_3, R.raw.video_4, R.raw.video_5, R.raw.video_6, R.raw.video_7, R.raw.video_8};
        ArrayList<Micro> microVideos = new ArrayList<>();
        Context context = Application.Companion.getInstance().getApplicationContext();
        String baseURI = "android.resource://" + context.getPackageName() + "/";
        for (int i = 0; i < imgs.length; i++) {
            Micro microVideo = new Micro();
            String imgeUri = Uri.parse(baseURI + imgs[i]).toString();
            String videosUri = Uri.parse(baseURI + videos[i]).toString();
            microVideo.setId(UUID.randomUUID().toString());
            microVideo.setImgAdvance(imgeUri);
            microVideo.setMicro(videosUri);
            microVideo.setUpper(Account.getUser());
            microVideos.add(microVideo);
        }
        DbHelper.save(Micro.class, microVideos.toArray(new Micro[0]));
    }

    public static void initLive() {
        User user = Account.getUser();
        ArrayList<Live> lives = new ArrayList<>();
        Live live = new Live();
        live.setId(UUID.randomUUID().toString());
        live.setTitle("show some");
        live.setSubTitle("Plato (427-347 BC) was a great philosopher of ancient Greece");
        live.setTime("18:00");
        live.setState(true);
        live.setUpper(user);
        lives.add(live);

        Live live1 = new Live();
        live1.setId(UUID.randomUUID().toString());
        live1.setTitle("have you");
        live1.setUpper(user);
        live1.setSubTitle("come to here to see me , i will let you happy");
        live1.setTime("7:00");
        live1.setState(false);
        lives.add(live1);


        Book book6 = new Book();
        book6.setId(UUID.randomUUID().toString());
        book6.setImage("https://img9.doubanio.com/view/subject/l/public/s8912644.jpg");
        book6.setName("Bible");
        book6.setAuthor("Christian");
        book6.setPrice(15);
        book6.setSales(2);
        book6.setCount(10);
        book6.setType(3);
        book6.setUpper(user);
        book6.setUpTime(new Date());
        book6.setModifyAt(new Date());
        book6.setDescription("Didn't we say to you in Egypt, 'Leave us alone; let us " +
                "serve the Egyptians'? It would have been better for us to serve the " +
                "Egyptians than to die in the desert!");
        DbHelper.save(Book.class, book6);
        DbHelper.save(Live.class, lives.toArray(new Live[0]));

    }
}
