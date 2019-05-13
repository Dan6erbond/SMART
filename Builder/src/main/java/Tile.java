import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tile {

    private String name;
    private String description;
    private String code;

    public Tile(File file, String lang) throws IOException {
        String json = FileHandler.readFile(file);
        JSONObject object = new JSONObject(json);
        code = object.getString("code");

        String fileName = file.getName().replace(".tile", "");

        if (lang != null && !lang.isEmpty()) {
            Pattern namePattern = Pattern.compile(code + ".name=\"(\\w+)\"");
            Matcher nameMatcher = namePattern.matcher(lang);

            Pattern descPattern = Pattern.compile(code + ".description=\"(\\w+)\"");
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

    public String getName() {
        return name;
    }

    public String getLang() {
        String langName = code + ".name=\"" + name + "\"";
        String langDescription = description + ".description=\"" + description + "\"";
        return langName + "\n" + langDescription;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

}