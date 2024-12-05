package org.model.googlebookapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.model.book.ManageBook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleBooksAPI {

    public static final String BASE_URL = "https://www.googleapis.com/books/v1/";

    // Phương thức tìm kiếm và trả về thông tin sách
    public String searchAndParseBooks(String query) {
        try {
            String urlString = BASE_URL + "volumes?q=" + query.replace(" ", "+");
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Phân tích và trả về thông tin sách
                return parseBookInfo(response.toString());
            } else {
                System.out.println("Error: " + responseCode);
                return "Error: Unable to fetch books.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Exception occurred while fetching books.";
        }
    }

    // Phương thức phân tích và lấy thông tin sách
    private String parseBookInfo(String jsonResponse) {
        try {
            StringBuilder bookInfo = new StringBuilder();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                int numberBook = 100; // Giá trị mặc định vì API không cung cấp số lượng sách
                String title = volumeInfo.optString("title", "Unknown Title");
                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                String authors = authorsArray != null ? String.join(", ",
                        authorsArray.toList().toArray(new String[0])) : "Unknown Authors";
                String publisher = volumeInfo.optString("publisher", "Unknown Publisher");
                String publishedDate = volumeInfo.optString("publishedDate", "Unknown Date");
                String description = volumeInfo.optString("description", "No Description");
                JSONObject imageLinksObject = volumeInfo.optJSONObject("imageLinks");
                String imageLinks = imageLinksObject != null ?
                        imageLinksObject.optString("thumbnail", "No Image") : "No Image";
                int pageCount = volumeInfo.optInt("pageCount", 0);
                String language = volumeInfo.optString("language", "Unknown Language");

                bookInfo.append(numberBook).append("\t");
                bookInfo.append(title).append("\t");
                bookInfo.append("BO")
                        .append(ManageBook.getBooks().keySet().size() + 2).append("\t");
                bookInfo.append(authors).append("\t");
                bookInfo.append(publisher).append("\t");
                bookInfo.append(publishedDate).append("\t");
                bookInfo.append(description).append("\t");
                bookInfo.append(imageLinks).append("\t");
                bookInfo.append(pageCount).append("\t");
                bookInfo.append(language).append("\t");
                bookInfo.append("\n");

            }

            return bookInfo.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing book information.";
        }
    }

    public static void main(String[] args) {
        GoogleBooksAPI api = new GoogleBooksAPI();

        // Tìm kiếm và trả về thông tin sách
        String result = api.searchAndParseBooks("Java programming");
        System.out.println(result);
    }
}