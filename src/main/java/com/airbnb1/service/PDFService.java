package com.airbnb1.service;

import com.airbnb1.dto.BookingDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {
    private static final String PDF_DIRECTORY = "/path/to/your/pdf/directory";
    public void generatePDF(String fileName, BookingDto dto){//this file name iTextHelloWorld.pdf is come from here
        try {
            //Generate unique filename for the PDF
            Document document = new Document();//firstly create a document object
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
//pdfWriter class  comes from itext pdf //why output because it deal with pdf file xl file etc...
            document.open();//opening of the document
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);//and here setting of the font and color. what you font want in your PDF
            Chunk bookingConfirmation = new Chunk("Booking Confirmation", font);
            Chunk guestName = new Chunk("Guest Name: "+dto.getGuestName(), font);
            Chunk price = new Chunk("Price Per Night"+dto.getPrice(), font);
            Chunk totalPrice = new Chunk("Total Price"+dto.getTotalPrice(), font);

            document.add(bookingConfirmation);
            document.add(new Paragraph("/n"));
            document.add(guestName);
            document.add(new Paragraph("/n"));
            document.add(price);
            document.add(new Paragraph("/n"));
            document.add(totalPrice);


            document.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
    }
}
