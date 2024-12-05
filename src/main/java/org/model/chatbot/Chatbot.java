package org.model.chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Chatbot {
    public String getPythonResponse(String inputData) {
        String file = "D:\\Library-Management\\src\\main\\java\\org\\model\\chatbot\\chatbot_demo.py";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", file, inputData);
            processBuilder.redirectErrorStream(true);  // Chuyển lỗi về cùng luồng với đầu ra

            // Khởi động tiến trình
            Process process = processBuilder.start();

            // Đọc kết quả trả về từ Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Đợi tiến trình Python kết thúc
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString().trim();  // Trả về kết quả từ Python
            } else {
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorOutput.append(errorLine).append("\n");
                }
                return "Có lỗi xảy ra trong quá trình thực thi Python:\n"
                        + errorOutput.toString();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Có lỗi xảy ra trong quá trình thực thi Python.";
        }
    }
}


//    public static void main(String[] args) {
//        // Tạo đối tượng để đọc dữ liệu từ người dùng
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String inputData;
//
//        System.out.println("ChatBot: Chào bạn! Tôi có thể giúp gì cho bạn, gõ 'exit' để kết thúc.");
//        Chatbot chatbot = new Chatbot();
//        try {
//            // Lặp lại việc yêu cầu người dùng nhập câu hỏi cho đến khi họ nhập "exit"
//            while (true) {
//                System.out.print("Bạn: ");
//                inputData = reader.readLine();
//
//                // Kiểm tra nếu người dùng muốn thoát
//                if ("exit".equalsIgnoreCase(inputData)) {
//                    System.out.println("ChatBot: Tạm biệt!");
//                    break;  // Thoát khỏi vòng lặp khi người dùng nhập 'exit'
//                }
//
//                // Gửi câu hỏi đến Python và nhận câu trả lời
//                String response = chatbot.getPythonResponse(inputData);
//                System.out.println("ChatBot: " + response);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
