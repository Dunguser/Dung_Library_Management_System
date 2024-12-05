package org.model.googlebookapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GoogleBooksImageFetcher {
    public static final String API_KEY
            = "AIzaSyBC9N5O6hJHpKvSZgOYGLvE-ckYgAGFOuw";
    public static final String INPUT_FILE
            = "D:\\Library-Management\\src\\main\\resources\\filetxt\\listBook.txt";
    public static final String OUTPUT_FILE
            = "D:\\Library-Management\\src\\main\\resources\\filetxt\\updatedListBook.txt";

    private static String fetchImageLink(String title) {
        try {
            // Mã hóa tiêu đề sách để sử dụng trong URL
            String query = java.net.URLEncoder.encode(title, "UTF-8");
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q="
                    + query + "&key=" + API_KEY;

            // Gửi request HTTP
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Đọc response JSON
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Parse JSON để lấy link ảnh
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray items = jsonResponse.optJSONArray("items");
                if (items != null && items.length() > 0) {
                    JSONObject volumeInfo = items.getJSONObject(0)
                            .getJSONObject("volumeInfo");
                    if (volumeInfo.has("imageLinks")) {
                        return volumeInfo.getJSONObject("imageLinks")
                                .getString("thumbnail");
                    }
                }
            } else {
                System.out.println("API request failed with response code: "
                        + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}



//    public static void main(String[] args) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE));
//             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Parse thông tin sách từ file
//                String[] parts = line.split(",");
//                if (parts.length < 8) {
//                    System.out.println("Invalid format: " + line);
//                    continue;
//                }
//
//                String bookId = parts[0];
//                String title = parts[2];
//
//                // Gọi Google Books API để tìm kiếm sách
//                String imageLink = fetchImageLink(title);
//
//                // Cập nhật thông tin sách với link ảnh
//                if (imageLink != null) {
//                    parts[7] = imageLink; // Update the image link field
//                    System.out.println("Updated image link for book: " + title);
//                } else {
//                    System.out.println("No image found for book: " + title);
//                }
//
//                // Ghi thông tin cập nhật vào file output
//                writer.write(String.join(",", parts));
//                writer.newLine();
//            }
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
