import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class NhanVien {
    protected String hoTen;
    protected String diaChi;
    protected String gioiTinh;
    protected int namSinh;
    protected int soNamCongTac;
    protected double luongCoBan = 500000;

    public NhanVien(String hoTen, String diaChi, String gioiTinh, int namSinh, int soNamCongTac) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.namSinh = namSinh;
        this.soNamCongTac = soNamCongTac;
    }

    public double tinhLuong() {
        return 0;
    }
} 

class NVVanPhong extends NhanVien {
    private double heSoLuong;

    public NVVanPhong(String hoTen, String diaChi, String gioiTinh, int namSinh, int soNamCongTac, double heSoLuong) {
        super(hoTen, diaChi, gioiTinh, namSinh, soNamCongTac);
        this.heSoLuong = heSoLuong;
    }

    @Override
    public double tinhLuong() {
        double phuCap;
        if (soNamCongTac < 10) {
            phuCap = 1 * luongCoBan;
        } else {
            phuCap = 1.2 * luongCoBan;
        }
        return heSoLuong * luongCoBan + phuCap;
    }
}

class NVKinhDoanh extends NhanVien {
    private double doanhThu;
    private double phanTramHoaHong;

    public NVKinhDoanh(String hoTen, String diaChi, String gioiTinh, int namSinh, int soNamCongTac, double doanhThu,
            double phanTramHoaHong) {
        super(hoTen, diaChi, gioiTinh, namSinh, soNamCongTac);
        this.doanhThu = doanhThu;
        this.phanTramHoaHong = phanTramHoaHong;
    }

    @Override
    public double tinhLuong() {
        return luongCoBan + doanhThu * phanTramHoaHong;
    }
}

class CongNhan extends NhanVien {
    private double heSoLuong;
    private int sanLuong;

    public CongNhan(String hoTen, String diaChi, String gioiTinh, int namSinh, int soNamCongTac, double heSoLuong,
            int sanLuong) {
        super(hoTen, diaChi, gioiTinh, namSinh, soNamCongTac);
        this.heSoLuong = heSoLuong;
        this.sanLuong = sanLuong;
    }

    @Override
    public double tinhLuong() {
        double thuong;
        if (sanLuong < 1000) {
            thuong = 1 * luongCoBan;
        } else {
            thuong = 1.5 * luongCoBan;
        }
        return heSoLuong * luongCoBan + thuong;
    }
}

class QLNV {
    private List<NhanVien> nhanVienList;

    public QLNV() {
        this.nhanVienList = new ArrayList<>();
    }

    public void nhapNhanVien(int n) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= n; i++) {
            System.out.println("Nhập thông tin cho nhân viên thứ " + i);
            System.out.print("Loại nhân viên (VP/KD/CN): ");
            String loaiNV = scanner.nextLine();
            System.out.print("Họ và tên: ");
            String hoTen = scanner.nextLine();
            System.out.print("Địa chỉ: ");
            String diaChi = scanner.nextLine();
            System.out.print("Giới tính: ");
            String gioiTinh = scanner.nextLine();
            System.out.print("Năm sinh: ");
            int namSinh = scanner.nextInt();
            System.out.print("Số năm công tác: ");
            int soNamCongTac = scanner.nextInt();

            if (loaiNV.equalsIgnoreCase("VP")) {
                System.out.print("Hệ số lương: ");
                double heSoLuong = scanner.nextDouble();
                nhanVienList.add(new NVVanPhong(hoTen, diaChi, gioiTinh, namSinh, soNamCongTac, heSoLuong));
            } else if (loaiNV.equalsIgnoreCase("KD")) {
                System.out.print("Doanh thu: ");
                double doanhThu = scanner.nextDouble();
                System.out.print("Phần trăm hoa hồng: ");
                double phanTramHoaHong = scanner.nextDouble();
                nhanVienList.add(new NVKinhDoanh(hoTen, diaChi, gioiTinh, namSinh, soNamCongTac, doanhThu,
                        phanTramHoaHong));
            } else if (loaiNV.equalsIgnoreCase("CN")) {
                System.out.print("Hệ số lương: ");
                double heSoLuong = scanner.nextDouble();
                System.out.print("Sản lượng: ");
                int sanLuong = scanner.nextInt();
                nhanVienList.add(new CongNhan(hoTen, diaChi, gioiTinh, namSinh, soNamCongTac, heSoLuong, sanLuong));
            }
            scanner.nextLine();
        }
    }

    public void xuatThongTin() {
        for (NhanVien nv : nhanVienList) {
            System.out.println("--------------------------------------");
            System.out.println("Họ và tên: " + nv.hoTen);
            System.out.println("Địa chỉ: " + nv.diaChi);
            System.out.println("Giới tính: " + nv.gioiTinh);
            System.out.println("Năm sinh: " + nv.namSinh);
            System.out.println("Số năm công tác: " + nv.soNamCongTac);
            System.out.println("Lương: " + nv.tinhLuong());
            System.out.println("--------------------------------------");
        }
    }

    public List<NhanVien> timKiemTheoHoTen(String hoTen) {
        List<NhanVien> foundNV = new ArrayList<>();
        for (NhanVien nv : nhanVienList) {
            if (nv.hoTen.toLowerCase().contains(hoTen.toLowerCase())) {
                foundNV.add(nv);
            }
        }
        return foundNV;
    }

    public void hienThiNhanVienSinhNam2000() {
        for (NhanVien nv : nhanVienList) {
            if (nv.namSinh == 2000) {
                System.out.println(nv.hoTen);
            }
        }
    }

    public int demNhanVienSinhNam2000VaQueSG() {
        int count = 0;
        for (NhanVien nv : nhanVienList) {
            if (nv.namSinh == 2000 && nv.diaChi.equalsIgnoreCase("Sài Gòn")) {
                count++;
            }
        }
        return count;
    }

    public void hienThiNhanVienCoTongLuongDuoi3M() {
        for (NhanVien nv : nhanVienList) {
            if (nv.tinhLuong() < 3000000) {
                System.out.println(nv.hoTen);
            }
        }
    }

    public static void main(String[] args) {
        QLNV qlnv = new QLNV();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("------------ MENU ------------");
            System.out.println("1. Nhập thông tin nhân viên");
            System.out.println("2. Xuất thông tin nhân viên");
            System.out.println("3. Tìm kiếm theo họ tên");
            System.out.println("4. Hiển thị nhân viên sinh năm 2000");
            System.out.println("5. Đếm số lượng nhân viên sinh năm 2000 và quê ở Sài Gòn");
            System.out.println("6. Hiển thị nhân viên có tổng lương dưới 3,000,000");
            System.out.println("0. Thoát chương trình");

            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Nhập số lượng nhân viên: ");
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    qlnv.nhapNhanVien(n);
                    break;
                case 2:
                    qlnv.xuatThongTin();
                    break;
                case 3:
                    System.out.print("Nhập họ tên cần tìm kiếm: ");
                    String hoTen = scanner.nextLine();
                    List<NhanVien> foundNV = qlnv.timKiemTheoHoTen(hoTen);
                    if (foundNV.isEmpty()) {
                        System.out.println("Không tìm thấy nhân viên nào.");
                    } else {
                        System.out.println("Kết quả tìm kiếm:");
                        for (NhanVien nv : foundNV) {
                            System.out.println(nv.hoTen);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Danh sách nhân viên sinh năm 2000:");
                    qlnv.hienThiNhanVienSinhNam2000();
                    break;
                case 5:
                    int count = qlnv.demNhanVienSinhNam2000VaQueSG();
                    System.out.println("Số lượng nhân viên sinh năm 2000 và quê ở Sài Gòn: " + count);
                    break;
                case 6:
                    System.out.println("Danh sách nhân viên có tổng lương dưới 3,000,000:");
                    qlnv.hienThiNhanVienCoTongLuongDuoi3M();
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }

            System.out.println();
        } while (choice != 0);

        scanner.close();
    }
}
