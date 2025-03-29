package Util;

public class RatingUtil {
    
    /**
     * Generates HTML for displaying star ratings
     * @param rating The rating value (1-5)
     * @return HTML string with star icons
     */
    public static String getStarRatingHtml(int rating) {
        StringBuilder html = new StringBuilder();
        
        // Add filled stars based on rating
        for (int i = 0; i < rating; i++) {
            html.append("<i class=\"fas fa-star text-warning\"></i>");
        }
        
        // Add empty stars to complete 5 stars
        for (int i = rating; i < 5; i++) {
            html.append("<i class=\"far fa-star text-warning\"></i>");
        }
        
        return html.toString();
    }
    
    /**
     * Generates a CSS class for the rating to display different colors
     * @param rating The rating value (1-5)
     * @return CSS class string
     */
    public static String getRatingColorClass(int rating) {
        switch (rating) {
            case 1:
                return "text-danger";
            case 2:
                return "text-warning";
            case 3:
                return "text-secondary";
            case 4:
                return "text-primary";
            case 5:
                return "text-success";
            default:
                return "";
        }
    }
}
