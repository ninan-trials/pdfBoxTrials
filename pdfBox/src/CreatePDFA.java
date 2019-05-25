/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.planbase.pdf.layoutmanager.*;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static com.planbase.pdf.layoutmanager.CellStyle.Align.*;
import static java.awt.Color.*;

/**
 * Creates a simple PDF/A document.
 */
public final class CreatePDFA {
    private CreatePDFA() {
    }

    public static void main(String[] args) throws Exception {

        String file = "/Users/ninanjohn/Trials/pdfBoxTrials/trial.pdf";
        String message = "PDF Creation";
        String fontfile = "/Users/ninanjohn/Downloads/Andalus/2.ttf";


//        createSamplePdf(file, message, fontfile);
        createTableInPdf();
    }

    private static void createTableInPdf() throws IOException, COSVisitorException {

        PdfLayoutMgr manager = PdfLayoutMgr.newRgbPageMgr();

        LogicalPage logicalPage = manager.logicalPageStart();

        logicalPage.tableBuilder(XyOffset.of(40f, logicalPage.yPageTop()))
                .addCellWidths(vec(120f, 120f, 120f))
                .textStyle(TextStyle.of(PDType1Font.COURIER_BOLD_OBLIQUE, 12f, YELLOW.brighter()))
                .partBuilder().cellStyle(CellStyle.of(BOTTOM_CENTER, Padding.of(2),
                decode("#3366cc"), BorderStyle.of(BLACK)))
                .rowBuilder().addTextCells("First", "Second", "Third").buildRow()
                .buildPart()
                .partBuilder().cellStyle(CellStyle.of(MIDDLE_CENTER, Padding.of(2),
                decode("#ccffcc"),
                BorderStyle.of(DARK_GRAY)))
                .minRowHeight(120f)
                .textStyle(TextStyle.of(PDType1Font.COURIER, 12f, BLACK))
                .rowBuilder()
                .cellBuilder().align(TOP_LEFT).add("Line 1", "Line two", "Line three").buildCell()
                .cellBuilder().align(TOP_CENTER).add("Line 1", "Line two", "Line three").buildCell()
                .cellBuilder().align(TOP_RIGHT).add("Line 1", "Line two", "Line three").buildCell()
                .buildRow()
                .rowBuilder()
                .cellBuilder().align(MIDDLE_LEFT).add("Line 1", "Line two", "Line three").buildCell()
                .cellBuilder().align(MIDDLE_CENTER).add("Line 1", "Line two", "Line three").buildCell()
                .cellBuilder().align(MIDDLE_RIGHT).add("Line 1", "Line two", "Line three").buildCell()
                .buildRow()
                .rowBuilder()
                .cellBuilder().align(BOTTOM_LEFT).add("Line 1", "Line two", "Line three").buildCell()
                .cellBuilder().align(BOTTOM_CENTER).add("Line 1", "Line two", "Line three").buildCell()
                .cellBuilder().align(BOTTOM_RIGHT).add("Line 1", "Line two", "Line three").buildCell()
                .buildRow()
                .buildPart()
                .buildTable();

        logicalPage.commit();
        OutputStream os = new FileOutputStream("/Users/ninanjohn/Trials/pdfBoxTrials/tableTrial.pdf");

        manager.save(os);
    }

    public static <T> List<T> vec(T... ts) {
        return Arrays.asList(ts);
    }

//    private static void createSamplePdf(String file, String message, String fontfile) throws Exception {
//        try (PDDocument doc = new PDDocument()) {
//            PDPage page = new PDPage();
//            doc.addPage(page);
//
//            // load the font as this needs to be embedded
//            PDFont font = PDType0Font.load(doc, new File(fontfile));
//
//            // A PDF/A file needs to have the font embedded if the font is used for text rendering
//            // in rendering modes other than text rendering mode 3.
//            //
//            // This requirement includes the PDF standard fonts, so don't use their static PDFType1Font classes such as
//            // PDFType1Font.HELVETICA.
//            //
//            // As there are many different font licenses it is up to the developer to check if the license terms for the
//            // font loaded allows embedding in the PDF.
//            //
//            if (!font.isEmbedded()) {
//                throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
//                        + " text rendering in rendering modes other than rendering mode 3 are embedded.");
//            }
//
//            // create a page with the message
//            PDPageContentStream contents = new PDPageContentStream(doc, page);
//            contents.beginText();
//            contents.setFont(font, 12);
//            contents.newLineAtOffset(100, 700);
//            contents.showText(message);
//            contents.endText();
//            contents.saveGraphicsState();
//            contents.close();
//
//            // add XMP metadata
//            XMPMetadata xmp = XMPMetadata.createXMPMetadata();
//
//            try {
//                DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
//                dc.setTitle(file);
//
//                PDFAIdentificationSchema id = xmp.createAndAddPFAIdentificationSchema();
//                id.setPart(1);
//                id.setConformance("B");
//
//                XmpSerializer serializer = new XmpSerializer();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                serializer.serialize(xmp, baos, true);
//
//                PDMetadata metadata = new PDMetadata(doc);
//                metadata.importXMPMetadata(baos.toByteArray());
//                doc.getDocumentCatalog().setMetadata(metadata);
//            } catch (BadFieldValueException e) {
//                // won't happen here, as the provided value is valid
//                throw new IllegalArgumentException(e);
//            }
//
//            // sRGB output intent
//            InputStream colorProfile = new FileInputStream("/Users/ninanjohn/Downloads/sRGB.icc");
//            PDOutputIntent intent = new PDOutputIntent(doc, colorProfile);
//            intent.setInfo("sRGB IEC61966-2.1");
//            intent.setOutputCondition("sRGB IEC61966-2.1");
//            intent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
//            intent.setRegistryName("http://www.color.org");
//            doc.getDocumentCatalog().addOutputIntent(intent);
//
//            doc.save(file);
//        }
//    }
}
