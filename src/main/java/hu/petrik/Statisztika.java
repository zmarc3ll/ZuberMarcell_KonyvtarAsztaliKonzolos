package hu.petrik;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Statisztika {
    private Connection conn;
    private String DB_DRIVER = "mysql";
    private String DB_HOST = "localhost";
    private String DB_PORT = "3306";
    private String DB_DBNAME = "books";
    private String DB_USERNAME = "root";
    private String DB_PASSWORD = "";
    private List<Konyv> konyvek = new ArrayList<>();

    public Statisztika() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
        String sql = "SELECT * FROM books";
        Statement stmnt = conn.createStatement();
        ResultSet result = stmnt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String title = result.getString("title");
            String author = result.getString("author");
            int publish_year = result.getInt("publish_year");
            int page_count = result.getInt("page_count");
            Konyv konyv = new Konyv(id, title, author, publish_year, page_count);
            konyvek.add(konyv);
        }
    }

    public int elsoFeladat() {
        int szamlalo = 0;
        for (Konyv k : konyvek) {
            if (k.getPage_count() > 500) {
                szamlalo++;
            }
        }
        return szamlalo;
    }

    public int elsoFeladatStream() {
        Stream<Konyv> konyvStream = konyvek.stream();
        return (int) konyvStream.filter(konyv -> konyv.getPage_count() > 500).count();
    }

    public boolean masodikFeladat() {
        Stream<Konyv> konyvStream = konyvek.stream();
        return konyvStream.anyMatch(konyv -> konyv.getPublish_year() < 1950);
    }

    public Konyv harmadikFeladat() {
        Konyv legnagyobbKonyv = konyvek.get(0);
        for (Konyv k : konyvek) {
            if (legnagyobbKonyv.getPage_count() < k.getPage_count()) {
                legnagyobbKonyv = k;
            }
        }
        return legnagyobbKonyv;
    }
    public Konyv harmadikFeladatStream() {
        Stream<Konyv> konyvStream = konyvek.stream();
        return konyvStream.max(Comparator.comparing(Konyv::getPage_count)).orElseThrow(NoSuchElementException::new);
    }
    public String negyedikFeladatStream() {
        Stream<Konyv> konyvStream = konyvek.stream();
        return (konyvStream.collect(groupingBy(konyv -> konyv.getAuthor(), counting()))).entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    public void otodikFeladat() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Adjon meg egy könyv címet: ");
        String book = sc.nextLine().trim();
        String author = "";

        for (Konyv konyv : konyvek) {
            if (konyv.getTitle().equals(book)) {
                author = konyv.getAuthor();
            }
        }
        if (author.isEmpty()) {
            System.out.println("Nincs ilyen című könyv!");
            return;
        }
        System.out.println("Az megadott könyv szerzője: " + author);
    }

}
