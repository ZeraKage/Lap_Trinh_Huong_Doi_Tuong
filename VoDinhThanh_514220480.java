package hoten_mssv;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DecimalFormat;
import java.util.Date;

public class GiaoDich {
    private String maGiaoDich;
    private Date ngayGiaoDich;
    private double donGia;
    private int soLuong;

    public GiaoDich(String maGiaoDich, Date ngayGiaoDich, double donGia, int soLuong) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public double getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double tinhThanhTien() {
        return soLuong * donGia;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        return "Mã giao dịch: " + maGiaoDich +
                ", Ngày giao dịch: " + ngayGiaoDich +
                ", Đơn giá: " + df.format(donGia) +
                ", Số lượng: " + soLuong;
    }
}

public class GiaoDichTienTe extends GiaoDich {
    private String loaiTienTe;
    private double tyGia;

    public GiaoDichTienTe(String maGiaoDich, Date ngayGiaoDich, double donGia, int soLuong, String loaiTienTe, double tyGia) {
        super(maGiaoDich, ngayGiaoDich, donGia, soLuong);
        this.loaiTienTe = loaiTienTe;
        this.tyGia = tyGia;
    }

    public String getLoaiTienTe() {
        return loaiTienTe;
    }

    public double getTyGia() {
        return tyGia;
    }

    @Override
    public double tinhThanhTien() {
        if (loaiTienTe.equalsIgnoreCase("USD") || loaiTienTe.equalsIgnoreCase("Euro")) {
            return soLuong * donGia * tyGia;
        } else if (loaiTienTe.equalsIgnoreCase("VND")) {
            return super.tinhThanhTien();
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Loại tiền tệ: " + loaiTienTe +
                ", Tỷ giá: " + tyGia;
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<GiaoDich> danhSachGiaoDich = new ArrayList<>();

        while (true) {
            System.out.println("===== MENU =====");
            System.out.println("1. Nhập thông tin giao dịch vàng");
            System.out.println("2. Nhập thông tin giao dịch tiền tệ");
            System.out.println("3. Viết hàm kiểm tra đơn giá, số lượng, Tỷ giá không được nhập chữ và > 0.");
            System.out.println("4. Xuất tất cả giao dịch vàng và giao dịch tiền tệ.");
            System.out.println("5. Tính trung bình thành tiền của khách hàng giao dịch vàng.");
            System.out.println("6. Sắp xếp thành tiền trưởng của giao dịch tiền tệ và giảm dần theo lượng vàng.");
            System.out.println("7. Viết hàm kiểm tra mã giao dịch theo định dạng.");
            System.out.println("8. Nhập năm bắt đầu và năm kết thúc để xóa các giao dịch trong khoảng thời gian và có thành tiền lớn hai nhưng đứng ở vị trí chẵn trong mảng.");

            System.out.print("Chọn chức năng (hoặc 0 để thoát): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Thoát chương trình.");
                    return;
                case 1:
                    System.out.println("Nhập thông tin giao dịch vàng:");
                    nhapGiaoDichVang(scanner, danhSachGiaoDich);
                    break;
                case 2:
                    System.out.println("Nhập thông tin giao dịch tiền tệ:");
                    nhapGiaoDichTienTe(scanner, danhSachGiaoDich);
                    break;
                case 3:
                    System.out.println("Hàm kiểm tra đơn giá, số lượng, Tỷ giá không được nhập chữ và > 0.");
                    kiemTraGia(scanner);
                    break;
                case 4:
                    System.out.println("Danh sách các giao dịch:");
                    xuatDanhSachGiaoDich(danhSachGiaoDich);
                    break;
                case 5:
                    System.out.println("Trung bình thành tiền của khách hàng giao dịch vàng: " + tinhTrungBinhThanhTienVang(danhSachGiaoDich));
                    break;
                case 6:
                    System.out.println("Danh sách giao dịch tiền tệ được sắp xếp:");
                    sapXepGiaoDichTienTe(danhSachGiaoDich);
                    break;
                case 7:
                    System.out.println("Nhập mã giao dịch cần kiểm tra:");
                    String maGiaoDich = scanner.nextLine();
                    boolean isValidFormat = kiemTraMaGiaoDich(maGiaoDich);
                    System.out.println("Mã giao dịch " + maGiaoDich + (isValidFormat ? " hợp lệ." : " không hợp lệ."));
                    break;
                case 8:
                    System.out.println("Nhập năm bắt đầu:");
                    int namBatDau = scanner.nextInt();
                    System.out.println("Nhập năm kết thúc:");
                    int namKetThuc = scanner.nextInt();
                    scanner.nextLine();
                    xoaGiaoDichTheoNam(danhSachGiaoDich, namBatDau, namKetThuc);
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ.");
            }
        }
    }

    public static void nhapGiaoDichVang(Scanner scanner, List<GiaoDich> danhSachGiaoDich) {
        System.out.print("Nhập mã giao dịch: ");
        String maGiaoDich = scanner.nextLine();

        System.out.print("Nhập ngày giao dịch (theo định dạng dd/MM/yyyy): ");
        String ngayGiaoDichStr = scanner.nextLine();
        Date ngayGiaoDich = parseDate(ngayGiaoDichStr);

        System.out.print("Nhập đơn giá: ");
        double donGia = scanner.nextDouble();

        System.out.print("Nhập số lượng: ");
        int soLuong = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập loại vàng: ");
        String loaiVang = scanner.nextLine();

        GiaoDichVang giaoDichVang = new GiaoDichVang(maGiaoDich, ngayGiaoDich, donGia, soLuong, loaiVang);
        danhSachGiaoDich.add(giaoDichVang);
    }

    public static void nhapGiaoDichTienTe(Scanner scanner, List<GiaoDich> danhSachGiaoDich) {
        System.out.print("Nhập mã giao dịch: ");
        String maGiaoDich = scanner.nextLine();

        System.out.print("Nhập ngày giao dịch (theo định dạng dd/MM/yyyy): ");
        String ngayGiaoDichStr = scanner.nextLine();
        Date ngayGiaoDich = parseDate(ngayGiaoDichStr);

        System.out.print("Nhập đơn giá: ");
        double donGia = scanner.nextDouble();

        System.out.print("Nhập số lượng: ");
        int soLuong = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập loại tiền tệ (VND, USD, Euro): ");
        String loaiTienTe = scanner.nextLine();

        double tyGia = 1.0;
        if (!loaiTienTe.equalsIgnoreCase("VND")) {
            System.out.print("Nhập tỷ giá: ");
            tyGia = scanner.nextDouble();
        }

        GiaoDichTienTe giaoDichTienTe = new GiaoDichTienTe(maGiaoDich, ngayGiaoDich, donGia, soLuong, loaiTienTe, tyGia);
        danhSachGiaoDich.add(giaoDichTienTe);
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void kiemTraGia(Scanner scanner) {
        System.out.print("Nhập đơn giá: ");
        double donGia = kiemTraSoThuc(scanner);

        System.out.print("Nhập số lượng: ");
        int soLuong = kiemTraSoNguyenDuong(scanner);

        System.out.print("Nhập tỷ giá (nhập 1 nếu là tiền VND): ");
        double tyGia = kiemTraSoThuc(scanner);
    }

    public static double kiemTraSoThuc(Scanner scanner) {
        double number;
        while (true) {
            try {
                number = Double.parseDouble(scanner.nextLine());
                if (number <= 0) {
                    System.out.print("Nhập lại (phải lớn hơn 0): ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Nhập lại (phải là số): ");
            }
        }
        return number;
    }

    public static int kiemTraSoNguyenDuong(Scanner scanner) {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number <= 0) {
                    System.out.print("Nhập lại (phải là số nguyên dương): ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.print("Nhập lại (phải là số nguyên dương): ");
            }
        }
        return number;
    }

    public static void xuatDanhSachGiaoDich(List<GiaoDich> danhSachGiaoDich) {
        for (GiaoDich gd : danhSachGiaoDich) {
            System.out.println(gd);
        }
    }

    public static double tinhTrungBinhThanhTienVang(List<GiaoDich> danhSachGiaoDich) {
        double sum = 0;
        int count = 0;
        for (GiaoDich gd : danhSachGiaoDich) {
            if (gd instanceof GiaoDichVang) {
                sum += gd.tinhThanhTien();
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    public static void sapXepGiaoDichTienTe(List<GiaoDich> danhSachGiaoDich) {
        Collections.sort(danhSachGiaoDich, new Comparator<GiaoDich>() {
            @Override
            public int compare(GiaoDich gd1, GiaoDich gd2) {
                double thanhTien1 = gd1.tinhThanhTien();
                double thanhTien2 = gd2.tinhThanhTien();
                if (thanhTien1 < thanhTien2) {
                    return 1;
                } else if (thanhTien1 > thanhTien2) {
                    return -1;
                } else {
                    if (gd1 instanceof GiaoDichTienTe && gd2 instanceof GiaoDichTienTe) {
                        int soLuongVang1 = ((GiaoDichTienTe) gd1).getSoLuong();
                        int soLuongVang2 = ((GiaoDichTienTe) gd2).getSoLuong();
                        return Integer.compare(soLuongVang2, soLuongVang1);
                    }
                    return 0;
                }
            }
        });

        xuatDanhSachGiaoDich(danhSachGiaoDich);
    }

    public static boolean kiemTraMaGiaoDich(String maGiaoDich) {
        String regex = "^[0-9]{2}[A-Za-z]-[0-9]{3}\\.[0-9]{2}[VTvt]$";
        return maGiaoDich.matches(regex);
    }

    public static void xoaGiaoDichTheoNam(List<GiaoDich> danhSachGiaoDich, int namBatDau, int namKetThuc) {
        Iterator<GiaoDich> iterator = danhSachGiaoDich.iterator();
        while (iterator.hasNext()) {
            GiaoDich gd = iterator.next();
            int year = gd.getNgayGiaoDich().getYear() + 1900;
            if (year >= namBatDau && year <= namKetThuc) {
                int index = danhSachGiaoDich.indexOf(gd);
                if (index % 2 == 0) {
                    iterator.remove();
                }
            }
        }
    }
}
