import java.io.File;
import java.io.IOException;

public interface StaxelObject {

    String getCode();
    void setCode(String code);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    String getLang();

    void save() throws IOException;

}
