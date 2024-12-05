import google.generativeai as genai
import sys
import io

import os
import grpc

# Đặt mức log của gRPC
os.environ["GRPC_VERBOSITY"] = "NONE"
os.environ["GRPC_LOG_SEVERITY"] = "ERROR"

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Kiểm tra xem dữ liệu đầu vào có tồn tại không
if len(sys.argv) < 2:
    print("No input data provided.")
    sys.exit(1)

input_data = sys.argv[1]

try:
    genai.configure(api_key="AIzaSyDZQ4RefbzF1sWlDI1jOtd9fMg0PS7lwM8")
    model = genai.GenerativeModel("gemini-1.5-flash")

    response = model.generate_content(input_data)

    print(response.text)  # In ra kết quả trả về từ API
except Exception as e:
    print(f"Error occurred in Python script: {e}")
    sys.exit(1)
