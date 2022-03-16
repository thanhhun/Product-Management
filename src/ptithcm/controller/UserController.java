package ptithcm.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.ChiNhanh;
import ptithcm.entity.DangNhap;
import ptithcm.entity.NhanVien;
import ptithcm.entity.PhanQuyen;

@Transactional
@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	SessionFactory factory;

	// lấy dữ liệu
	@RequestMapping(value = "index/{id_NV}.htm", method = RequestMethod.GET)
	public String indexGET(ModelMap model, @PathVariable("id_NV") Integer id) {
		NhanVien nhanVien = this.getNhanVienByID(id);
		DangNhap dangNhap = this.getDangNhapByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
		model.addAttribute("user", dangNhap);
		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/index";
	}

	// khi kích hoạt một chức năng gì đó (như nút hủy trong form)
	@RequestMapping(value = "index/{id_NV}.htm", method = RequestMethod.POST)
	public String indexPOST(ModelMap model, @PathVariable("id_NV") Integer id) {
		NhanVien nhanVien = this.getNhanVienByID(id);
		DangNhap dangNhap = this.getDangNhapByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
		model.addAttribute("user", dangNhap);
		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/index";
	}

	@RequestMapping(value = "changepassword/{id_NV}.htm", method = RequestMethod.GET)
	public String changepassGet(ModelMap model, @PathVariable("id_NV") Integer id) {
		DangNhap dangNhap = this.getDangNhapByID(id);
		model.addAttribute("hoTen", dangNhap.getNhanVien().getHo() + " " + dangNhap.getNhanVien().getTen());
		model.addAttribute("id_NV", id);
		// Kiểm tra đăng nhập theo quyền nào
		if (dangNhap.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
		}
		return "user/changepassword";
	}

	@RequestMapping(value = "changepass/{id_NV}.htm", method = RequestMethod.POST)
	public String changepassPost(ModelMap model, @PathVariable("id_NV") Integer id,
			@RequestParam("currentpw") String currentpw, @RequestParam("newpw") String newpw,
			@RequestParam("checkpw") String checkpw) {
		DangNhap dangNhapCu = this.getDangNhapByID(id);
		model.addAttribute("check", 0); // kiểm tra thông báo
		if (currentpw.equals(dangNhapCu.getMatKhau()) == false) {
			model.addAttribute("message1", "Mật khẩu hiện tại không đúng!");
			model.addAttribute("id_NV", dangNhapCu.getNhanVien().getMaNV());
			model.addAttribute("hoTen", dangNhapCu.getNhanVien().getHo() + " " + dangNhapCu.getNhanVien().getTen());
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhapCu.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			} else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			}

			return "user/changepassword";
		}
		if (checkpw.equals(newpw) == false) {
			model.addAttribute("message2", "Xác nhận mật khẩu không trùng nhau! Vui lòng thử lại!!");
			model.addAttribute("id_NV", dangNhapCu.getNhanVien().getMaNV());
			model.addAttribute("hoTen", dangNhapCu.getNhanVien().getHo() + " " + dangNhapCu.getNhanVien().getTen());
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhapCu.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			} else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhapCu.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			}
			return "user/changepassword";
		} else {
			model.addAttribute("check", 1); // kiểm tra thông báo

			// Đổi mật khẩu mới rồi update lại
			DangNhap dangNhapmoi = dangNhapCu;
			dangNhapmoi.setMatKhau(newpw);
			model.addAttribute("check", 1); // kiểm tra thông báo
			// Trả về họ tên và id_NV cho left-sidenav và top-bar
			model.addAttribute("id_NV", dangNhapmoi.getNhanVien().getMaNV());
			model.addAttribute("hoTen", dangNhapmoi.getNhanVien().getHo() + " " + dangNhapmoi.getNhanVien().getTen());
			model.addAttribute("notify", "Thay đổi mật khẩu thành công!");
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhapCu.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhapmoi.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			} else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhapmoi.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			}
			return "user/changepassword";
		}
	}

	@RequestMapping(value = "setting/{id_NV}.htm", method = RequestMethod.GET)
	public String settingUser(ModelMap model, @PathVariable("id_NV") Integer id) {
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Nếu admin thì cho chọn chi nhánh
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Mở phần chọn chi nhánh
			model.addAttribute("showCN", "open");
		} else {
			model.addAttribute("chiNhanh", nhanVien.getChiNhanh().getMaCN());
		}

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		System.out.println(nhanVien.getChiNhanh().getDiaChi());

		model.addAttribute("NhanVien", nhanVien);
		return "user/editstaff";
	}

	@RequestMapping(value = "edit", params = "btnEdit", method = RequestMethod.POST)
	public String editInfo(ModelMap model, @ModelAttribute("NhanVien") NhanVien nv) {
		Integer check = this.updateNhanVien(nv);
		model.addAttribute("check", check); // kiểm tra thông báo
		if (check != 0) {
			model.addAttribute("message", "Cập nhật thành công!");
		} else {
			model.addAttribute("message", "Cập nhật thất bại");
		}

		// Kiểm tra phân quyền và cho phép chọn chi nhánh
		if (nv.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Mở phần chọn chi nhánh
			model.addAttribute("showCN", "open");
			model.addAttribute("phanQuyen", "admin");
		} else {
			model.addAttribute("chiNhanh", nv.getChiNhanh().getMaCN());
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// truyền id cho các thông tin để xử lý profile và lock form
		model.addAttribute("id_NV", nv.getMaNV());
		// truyền tên cho top-bar
		model.addAttribute("hoTen", nv.getHo() + " " + nv.getTen());
		return "user/editstaff";
	}

	@RequestMapping(value = "list/{id_NV}.htm", method = RequestMethod.GET)
	public String listUser(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("user") NhanVien form) {

		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/listuser";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnAdd")
	public String addNhanVien(ModelMap model, @ModelAttribute("user") NhanVien newNhanVien,
			@PathVariable("id_NV") Integer id) {

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Kiểm tra đăng nhập theo quyền nào ở left-sidenav
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}

		Integer check = this.addNhanVien(newNhanVien);
		model.addAttribute("check", check);

		if (check != 0) {
			model.addAttribute("message", "Thêm nhân viên thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		// chạy lại list nhân viên để làm mới
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		return "user/listuser";
	}

	@RequestMapping(value = "list/{maNV}/{id_NV}.htm", params = "lnkEdit")
	public String editVatTu(ModelMap model, @PathVariable("maNV") Integer maNV, @PathVariable("id_NV") Integer id) {
		// Đẩy dữ liệu vào form user để cập nhật
		NhanVien chooseNV = this.getNhanVienByID(maNV);
		model.addAttribute("user", chooseNV);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}

		// chạy lại list nhân viên để làm mới
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		// Chuyển sang nút update để thực hiện chỉnh sửa vật tư
		model.addAttribute("btnAction", "btnUpdate");

		return "user/listuser";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnUpdate")
	public String updateNhanVien(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("user") NhanVien nv) {
		Integer check = this.updateNhanVien(nv);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật nhân viên thành công!");
		} else {
			model.addAttribute("message", "Cập nhật có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}

		// chạy lại list nhân viên để làm mới
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "user/listuser";
	}

	@RequestMapping(value = "list/{maNV}/{id_NV}.htm", params = "lnkDel")
	public String lnkDelPhieuXuat(ModelMap model, @PathVariable("maNV") Integer maNV,
			@PathVariable("id_NV") Integer id) {

		model.addAttribute("user", this.getNhanVienByID(maNV));
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}

		// chạy lại list nhân viên để làm mới
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnDelete");

		return "user/listuser";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnDelete")
	public String delNhanVien(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("user") NhanVien nv) {
		Integer check = this.deleteNhanVien(nv);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa nhân viên thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}

		// chạy lại list nhân viên để làm mới
		List<NhanVien> nhanViens = this.getNhanViens();
		model.addAttribute("nhanViens", nhanViens);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "user/listuser";
	}

	@RequestMapping(value = "createaccount/{id_NV}.htm", method = RequestMethod.GET)
	public String showFormCreateAccount(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dangnhap") DangNhap dangNhap) {

		List<DangNhap> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/createaccount";
	}

	@RequestMapping(value = "createaccount/action/{id_NV}.htm", params = "btnAdd")
	public String createAccount(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dangnhap") DangNhap dangNhap) {

		Integer check = this.addDangNhap(dangNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Tạo tài khoản thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		List<DangNhap> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/createaccount";
	}

	@RequestMapping(value = "createaccount/{edit_maNV}/{id_NV}.htm", params = "lnkEdit")
	public String editAccount(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("edit_maNV") Integer editMaNV) {

		model.addAttribute("dangnhap", this.getDangNhapByID(editMaNV));
		
		List<DangNhap> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnUpdate");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/createaccount";
	}

	@RequestMapping(value = "createaccount/action/{id_NV}.htm", params = "btnUpdate")
	public String updateAccount(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dangnhap") DangNhap dangNhap) {

		Integer check = this.updateDangNhap(dangNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật tài khoản thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		List<DangNhap> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/createaccount";
	}
	
	@RequestMapping(value = "createaccount/{edit_maNV}/{id_NV}.htm", params = "lnkDel")
	public String delAccount(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("edit_maNV") Integer editMaNV) {

		model.addAttribute("dangnhap", this.getDangNhapByID(editMaNV));
		
		List<DangNhap> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnDelete");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/createaccount";
	}
	
	@RequestMapping(value = "createaccount/action/{id_NV}.htm", params = "btnDelete")
	public String deleteAccount(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dangnhap") DangNhap dangNhap) {

		Integer check = this.deleteDangNhap(dangNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa tài khoản thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		List<DangNhap> DangNhaps = this.getDangNhaps();
		model.addAttribute("DangNhaps", DangNhaps);

		List<NhanVien> NhanViens = this.getNhanViens();
		model.addAttribute("NhanViens", NhanViens);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/createaccount";
	}
	
	@RequestMapping(value = "chinhanh/{id_NV}.htm", method = RequestMethod.GET)
	public String showChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/chinhanh";
	}

	@RequestMapping(value = "chinhanh/action/{id_NV}.htm", params = "btnAdd")
	public String addchiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {

		Integer check = this.addChiNhanh(chiNhanh);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Thêm chi nhánh thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		
		model.addAttribute("chiNhanhs", this.getChiNhanhs());
		
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
		
		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/chinhanh";
	}

	@RequestMapping(value = "chinhanh/{maCN}/{id_NV}.htm", params = "lnkEdit")
	public String editChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("maCN") String maCN) {

		model.addAttribute("chinhanh", this.getChiNhanhByMaCN(maCN));
		
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnUpdate");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/chinhanh";
	}
	
	@RequestMapping(value = "chinhanh/action/{id_NV}.htm", params = "btnUpdate")
	public String updateChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {

		Integer check = this.updateChiNhanh(chiNhanh);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật chi nhánh thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
	
		model.addAttribute("chiNhanhs", this.getChiNhanhs());
		
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/chinhanh";
	}
	
	@RequestMapping(value = "chinhanh/{maCN}/{id_NV}.htm", params = "lnkDel")
	public String delChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("maCN") String maCN) {

		model.addAttribute("chinhanh", this.getChiNhanhByMaCN(maCN));
		
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnDelete");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/chinhanh";
	}
	
	@RequestMapping(value = "chinhanh/action/{id_NV}.htm", params = "btnDelete")
	public String deleteChiNhanh(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chinhanh") ChiNhanh chiNhanh) {

		Integer check = this.deleteChiNhanh(chiNhanh);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa chi nhánh thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		model.addAttribute("chiNhanhs", this.getChiNhanhs());
		
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra đăng nhập theo quyền nào
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
		{
			model.addAttribute("phanQuyen", "admin");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		} else {
			model.addAttribute("phanQuyen", "nhanvien");
			System.out.println(nhanVien.getPhanQuyen().getTenPhanQuyen());
		}
		return "user/chinhanh";
	}
	
	@ModelAttribute("chiNhanhs")
	public List<ChiNhanh> getChiNhanhs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiNhanh";
		Query query = session.createQuery(hql);
		List<ChiNhanh> list = query.list();
		return list;
	}
	
	public ChiNhanh getChiNhanhByMaCN(String maCN) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiNhanh WHERE maCN= :maCN";
		Query quey = session.createQuery(hql);
		quey.setParameter("maCN", maCN);

		ChiNhanh chiNhanh = (ChiNhanh) quey.list().get(0);
		return chiNhanh;
	}
	
	
	@ModelAttribute("phanQuyens")
	public List<PhanQuyen> getPhanQuyens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhanQuyen";
		Query query = session.createQuery(hql);
		List<PhanQuyen> list = query.list();
		return list;
	}

	public List<NhanVien> getNhanViens() {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien";
		Query query = session.createQuery(hql);
		List<NhanVien> list = query.list();
		return list;
	}

	public NhanVien getNhanVienByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien n WHERE n.maNV= :id";
		Query quey = session.createQuery(hql);
		quey.setParameter("id", id);

		NhanVien nhanVien = (NhanVien) quey.list().get(0);
		return nhanVien;
	}

	public DangNhap getDangNhapByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DangNhap d WHERE d.nhanVien.maNV= :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		DangNhap dangNhap = (DangNhap) query.list().get(0);
		return dangNhap;
	}

	public List<DangNhap> getDangNhaps() {
		Session session = factory.getCurrentSession();
		String hql = "FROM DangNhap";
		Query query = session.createQuery(hql);
		List<DangNhap> list = query.list();
		return list;
	}

	public Integer updateDangNhap(DangNhap dangNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(dangNhap);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return 1;
	}

	public Integer addNhanVien(NhanVien nhanVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(nhanVien);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer updateNhanVien(NhanVien nhanVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(nhanVien);
			t.commit();

		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return 1;
	}

	public Integer deleteNhanVien(NhanVien nhanVien) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(nhanVien);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer addDangNhap(DangNhap dangNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(dangNhap);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer deleteDangNhap(DangNhap dangNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(dangNhap);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
	
	public Integer addChiNhanh(ChiNhanh chiNhanh) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(chiNhanh);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer updateChiNhanh(ChiNhanh chiNhanh) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(chiNhanh);
			t.commit();

		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return 1;
	}

	public Integer deleteChiNhanh(ChiNhanh chiNhanh) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(chiNhanh);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

}
