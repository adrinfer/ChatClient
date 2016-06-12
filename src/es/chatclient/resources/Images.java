/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.resources;

import javafx.scene.image.Image;

/**
 *
 * @author adrinfer
 */
public class Images {

    public final static String CLOSE_ICON = "/es/chatclient/resources/closeIconTransparent.png";
    public final static String CLOSE_ICON_HOVER = "/es/chatclient/resources/closeIconHoverTransparent.png";
    public final static String MAX_ICON = "/es/chatclient/resources/maxIconTransparent.png";
    public final static String MAX_ICON_HOVER = "/es/chatclient/resources/maxIconHoverTransparent.png";
    public final static String RESIZE_ICON = "/es/chatclient/resources/resizeIcon.png";
    public final static String RESIZE_ICON_HOVER = "/es/chatclient/resources/resizeIconHover.png";
    public final static String RESIZE_ICON_PRESSED = "/es/chatclient/resources/resizeIconPressed.png";
    public final static String MINIMIZE_ICON = "/es/chatclient/resources/minimizeIcon.png";
    public final static String MINIMIZE_ICON_HOVER = "/es/chatclient/resources/minimizeIconHover.png";
    
    public final static String APP_ICON = "/es/chatclient/resources/logoIcon2.png";
    

    public static Image getImage(String url) {
        return new Image(url);
    }

}
