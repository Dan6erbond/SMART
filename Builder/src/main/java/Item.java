import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item implements StaxelObject {

    private File file;
    private String name;
    private String description;
    private String code;

    public Item(File file, String lang) throws IOException {
        this.file = file;

        String json = FileHandler.readFile(file);
        JSONObject object;
        try {
            object = new JSONObject(json);
        } catch (Exception e) {
            System.out.println(file.getPath());
            System.out.println(e);
            return;
        }
        code = object.getString("code");

        String fileName = file.getName().replace(".item", "");

        if (lang != null && !lang.isEmpty()) {
            Pattern namePattern = Pattern.compile(code + ".name=(.+)");
            Matcher nameMatcher = namePattern.matcher(lang);

            Pattern descPattern = Pattern.compile(code + ".description=(.+)");
            Matcher descMatcher = descPattern.matcher(lang);

            if (nameMatcher.find()) {
                name = nameMatcher.group(1);
            } else {
                name = fileName;
            }

            if (descMatcher.find()) {
                description = descMatcher.group(1);
            } else {
                description = fileName;
            }
        } else {
            name = fileName;
            description = fileName;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLang() {
        String langName = code + ".name=" + name;
        String langDescription = code + ".description=" + description;
        return langName + "\n" + langDescription;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void save() throws IOException {
        String json = FileHandler.readFile(file);
        JSONObject object = new JSONObject(json);
        object.put("code", code);

        FileHandler.writeFile(object.toString(4), file);
    }

}