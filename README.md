# Project-BigData - Finding-Average-Temperature-of-Each-Year-using-Hadoop-HDFS

## Tổng quan
Dự án này triển khai một ứng dụng Hadoop MapReduce để tính toán nhiệt độ trung bình hàng năm từ một tập dữ liệu nhiệt độ bề mặt các nước và thế giới theo tháng tháng. Chương trình đọc dữ liệu từ HDFS, xử lý thông qua các job MapReduce, và lưu kết quả trở lại HDFS.

## Cài đặt

### Yêu cầu hệ thống
- **Hadoop**: Phiên bản 3.3.6 hoặc mới hơn
- **Java**: OpenJDK 8 hoặc tương thích
- **Hệ điều hành**: Linux/Ubuntu (Windows Subsystem for Linux - WSL được hỗ trợ)

### Các bước cài đặt
#### 1. **Clone repository**:
   ```bash
   git clone <repo-url>
   cd <repo-folder>
  ```
#### 2. **Biên dịch mã nguồn**:
   ```bash
   javac -classpath `hadoop classpath` -d /path/to/output/classes AverageTemperature.java
   ```
#### 3. **Đóng gói thành file JAR**:
   ```bash
   jar -cvf AverageTemperature.jar -C /path/to/output/classes/ .
   ```
#### 4. **Chạy chương trình trên Hadoop**:
   ```bash
   hadoop jar AverageTemperature.jar AverageTemperature <input_path> <output_path>
   ```
<input_path>: Đường dẫn HDFS tới tập dữ liệu đầu vào.
<output_path>: Đường dẫn HDFS tới thư mục đầu ra.
#### 5. **Xem kết quả**:
   ```bash
   hadoop fs -cat <output_path>/part-*
   ```
#### Tập dữ liệu :
Dữ liệu được sử dụng từ [Our World In Data](https://ourworldindata.org/grapher/monthly-average-surface-temperatures-by-year)
Sử dụng tập dữ liệu: monthly-average-surface-temperatures-by-year.csv. Hãy upload file này lên HDFS trước khi chạy job.
   ```bash
   hadoop fs -put monthly-average-surface-temperatures-by-year.csv /user/hadoop/input
   ```
