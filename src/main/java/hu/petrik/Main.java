package hu.petrik;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Statisztika st= new Statisztika();
            System.out.println("500 oldalnál hosszabb könyvek száma: " + st.elsoFeladatStream());
            System.out.println(st.masodikFeladat() ? "Van 1950-nél régebbi könyv" : "Nincs 1950-nél régebbi könyv");
            String szerzo = st.harmadikFeladat().getAuthor();
            String cim = st.harmadikFeladat().getTitle();
            int kiadasEve = st.harmadikFeladat().getPublish_year();
            int oldalszam =st.harmadikFeladat().getPage_count();
            System.out.println("A leghosszabb könyv:\n \tSzerző: "+szerzo+"\n \tCím: "+cim+"\n \tKiadás éve: "+kiadasEve+"\n \tOldalszám: "+oldalszam);
            System.out.println("A legtöbb könyvvel rendelkező szerző: "+st.negyedikFeladatStream());
            st.otodikFeladat();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
