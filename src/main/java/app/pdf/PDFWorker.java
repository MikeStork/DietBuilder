package app.pdf;

import app.model.Item;
import app.model.Recipe;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFWorker {
    private static final int PAGE_MARGIN = 50;
    private static final int LINE_HEIGHT = 15;

    public static Boolean exportToPDF(List<Recipe> PDFModel){
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
//            File myFont = new File("C:\\PDF\\Lato-Light.ttf");
            File myFont = new File(System.getenv("FONT_PATH"));
            PDFont font0 = PDType0Font.load(document, myFont);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font0, 24);
            int yOffset = (int) (PDRectangle.A4.getHeight() - PAGE_MARGIN);

            contentStream.beginText();
            contentStream.newLineAtOffset(PAGE_MARGIN, yOffset);
            contentStream.showText("Shopping List");
            contentStream.newLineAtOffset(0, -LINE_HEIGHT);
            contentStream.endText();
            contentStream.setLineWidth(1);
            contentStream.moveTo(50, PDRectangle.A4.getHeight() - 60);
            contentStream.lineTo(PDRectangle.A4.getWidth() - 50, PDRectangle.A4.getHeight() - 60);
            contentStream.setStrokingColor(0, 0, 0);
            contentStream.stroke();

            yOffset -= LINE_HEIGHT * 2;
            for (Recipe recipe : PDFModel) {
                String type = recipe.getType();
                var items = recipe.getItems();

                if (yOffset - LINE_HEIGHT * (items.size() + 2) < PAGE_MARGIN) {

                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font0, 10);
                    yOffset = (int) PDRectangle.A4.getHeight() - PAGE_MARGIN;
                }

                yOffset -= LINE_HEIGHT;

                contentStream.setFont(font0, 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(PAGE_MARGIN, yOffset);
                contentStream.showText("  " + type.toString() + " - " + recipe.getName());
                contentStream.endText();
                contentStream.setFont(font0, 8);
                yOffset -= LINE_HEIGHT;
                contentStream.beginText();
                contentStream.newLineAtOffset(PAGE_MARGIN+20, yOffset);
                recipe.CalculateNutrients();
                contentStream.showText("    Carbs: " + String.format("%.2f",recipe.getCarbs()) + " g    Fats: " + String.format("%.2f",recipe.getFats()) + " g    Proteins: "+String.format("%.2f",recipe.getProteins())+" g ");
                contentStream.endText();

                yOffset -= LINE_HEIGHT; //space after category name

                contentStream.setFont(font0, 9);
                for (Item item : items) {
                    yOffset -= LINE_HEIGHT;
                    String itemText = String.format("-   %s - %d g", item.getName(), item.getWeight());
                    contentStream.beginText();
                    contentStream.newLineAtOffset(PAGE_MARGIN * 2, yOffset);
                    contentStream.showText(itemText);
                    contentStream.endText();
                }

                yOffset -= LINE_HEIGHT; //space between categories
            }

            contentStream.close();
            try{
            document.save(getPathToDesktop());
                document.close();
                return true;
            }catch (Exception e){

                document.close();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getPathToDesktop() {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop";
        return desktopPath + File.separator + "Shopping_List.pdf";
    }
}


