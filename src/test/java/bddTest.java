import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import fr.api2.bdd.Requetes;


public class bddTest{
    
    private String dbPath;
    private final String prefix = "C:\\Users\\erwan\\Musique\\IRISH";

    @Before
    public void getPath() {
        this.dbPath = new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append(System.getProperty("file.separator"))
                .append("music.db")
                .toString();
    }

    @Test
    public void getData() {
        Requetes r = new Requetes(this.dbPath, this.prefix);
        String[] res = r.getAll();
        assertEquals(50L, (long)res.length);
    }

    @Test
    public void changeDbPathAndResShouldBeNull() {
        this.dbPath = "";
        Requetes r = new Requetes(this.dbPath, this.prefix);
        String[] res = r.getAll();
        assertNull(res);
    }
}
