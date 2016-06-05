/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.styles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * Programa que a partir de un archivo properties con valores, y un css template
 * usando como variable dichos valores (del archivo properties).
 *
 * Genera un tercer fichero css sustituyendo los valores de las variables por
 * los asignados en el archivo properties.
 *
 * Uso: Cuando se quiere que muchos elementos se quieran sustituir por otro.
 *
 * @author Adrián Fernández Cano
 */
public class CssGenerator {

    private static Properties propCss;

    private static final String CONSTANTS_FILE = "cssProperties.properties";
    private static final String TEMPLATE_FILE_NAME = "styles.css";
    private static final String DIR_CONSTANTS_CSS = System.getProperty("user.dir") + File.separator + "src" + File.separator + "es" + File.separator + "chatclient" + File.separator + "styles" + File.separator + "csstemplates" + File.separator;
    private static final String DIR_TEMPLATE_CSS = System.getProperty("user.dir") + File.separator + "src" + File.separator + "es" + File.separator + "chatclient" + File.separator + "styles" + File.separator + "csstemplates" + File.separator;
    private static final String DIR_GENERATED_CSS = System.getProperty("user.dir") + File.separator + "src" + File.separator + "es" + File.separator + "chatclient" + File.separator + "styles" + File.separator + "cssgenerated" + File.separator;

    public static void main(String[] args) {

        File templateCssFile = new File(DIR_TEMPLATE_CSS + TEMPLATE_FILE_NAME);
        loadConstants(CONSTANTS_FILE);

        //Generated text with variables replaced using cssProperties and templaceCssFile
        String generatedText = replaceConstants(templateCssFile);

        if (generatedText != null) {

            createCssFile(templateCssFile, generatedText);

            //File newCssFile = new File(dirCss + f.getName().substring(0, f.getName().indexOf(".")) + suffix + ".css");
            //redefine font weights
            //redefineFontWeights(replacedFile, newCssFile);
        }

    }

    private static void loadConstants(String name) {
        propCss = new Properties();
        try {

            //load a properties file from class path, inside static method
            propCss.load(new FileInputStream(DIR_CONSTANTS_CSS + File.separator + name));

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String replaceConstants(File f) {

        FileInputStream fis;
        try {

            fis = new FileInputStream(f);
            String content = IOUtils.toString(fis, Charset.defaultCharset());

            for (Object key : propCss.keySet()) {
                content = content.replaceAll(key.toString(), propCss.get(key).toString());
            }

            fis.close();

            return content;

        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(CssGenerator.class.getName()).log(Level.SEVERE, null, ex);

        }
        catch (IOException ex) {
            Logger.getLogger(CssGenerator.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    //Create or remplace the created css used in the application
    private static File createCssFile(File f, String generatedText) {

        BufferedWriter writer = null;
        File outputFile = null;

        try {

            outputFile = new File(DIR_GENERATED_CSS + f.getName());
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outputFile), "utf-8"));
            writer.write(generatedText);

        }
        catch (IOException ex) {
            System.err.println("Error generando css file:" + f.getName());

        } finally {

            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException ex) {
                    Logger.getLogger(CssGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return outputFile;
    }

}
