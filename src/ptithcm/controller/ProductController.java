package ptithcm.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.bean.UploadFile;
import ptithcm.entity.ChiNhanh;
import ptithcm.entity.ChiTietDonDatHang;
import ptithcm.entity.ChiTietPhieuNhap;
import ptithcm.entity.ChiTietPhieuXuat;
import ptithcm.entity.DatHang;
import ptithcm.entity.Kho;
import ptithcm.entity.NhanVien;
import ptithcm.entity.PhieuNhap;
import ptithcm.entity.PhieuXuat;
import ptithcm.entity.VatTu;

@Transactional
@Controller
@RequestMapping("/product/")
public class ProductController {

	@Autowired
	SessionFactory factory;

	@Autowired
	UploadFile baseUpLoadFilePhoto;

	static Integer soLuongHienTai;

	@RequestMapping(value = "list/{id_NV}.htm", method = RequestMethod.GET)
	public String showListProduct(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("vattu") VatTu vatTu) {
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

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển sang chế độ add
		model.addAttribute("btnAction", "btnAdd");

		return "product/productlist";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnAdd")
	public String addVatTu(ModelMap model, @ModelAttribute("vattu") VatTu vatTu, @PathVariable("id_NV") Integer id,
			@RequestParam("phoToVT") MultipartFile filePhoto) {
		try {
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
			String filePhotoName = date + filePhoto.getOriginalFilename();
			String photoPath = baseUpLoadFilePhoto.getBasePath() + File.separator + filePhotoName;
			System.out.println("Photo file path is " + photoPath);
			filePhoto.transferTo(new File(photoPath));
			vatTu.setPhoTo(filePhotoName);

			Thread.sleep(6000); // Sleeping time
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer check = this.addVatTu(vatTu);
		model.addAttribute("check", check);

		if (check != 0) {
			model.addAttribute("message", "Thêm vật tư thành công!");
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

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		return "product/productlist";
	}

	@RequestMapping(value = "list/{maVT}/{id_NV}.htm", params = "lnkEdit")
	public String editVatTu(ModelMap model, @PathVariable("maVT") String maVT, @PathVariable("id_NV") Integer id) {
		// Đẩy dữ liệu vào form lapphieuxuat để thực hiện update
		VatTu vatTu = this.getVatTuByMaVT(maVT);
		model.addAttribute("vattu", vatTu);

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

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển sang nút update để thực hiện chỉnh sửa vật tư
		model.addAttribute("btnAction", "btnUpdate");

		return "product/productlist";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnUpdate")
	public String updateVatTu(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("vattu") VatTu vatTu,
			@RequestParam("phoToVT") MultipartFile filePhoto) {
		try {
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
			String filePhotoName = date + filePhoto.getOriginalFilename();
			String photoPath = baseUpLoadFilePhoto.getBasePath() + File.separator + filePhotoName;
			System.out.println("Photo file path is " + photoPath);
			filePhoto.transferTo(new File(photoPath));
			vatTu.setPhoTo(filePhotoName);

			Thread.sleep(6000); // Sleeping time
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(vatTu.getMaVT());
		Integer check = this.updateVatTu(vatTu);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật vật tư thành công!");
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

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/productlist";
	}

	@RequestMapping(value = "list/{maVT}/{id_NV}.htm", params = "lnkDel")
	public String deleteVatTu(ModelMap model, @PathVariable("id_NV") Integer id, @PathVariable("maVT") String maVT) {

		VatTu vatTu = this.getVatTuByMaVT(maVT);
		model.addAttribute("vattu", vatTu);

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

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		//
		model.addAttribute("btnAction", "btnDelete");

		return "product/productlist";
	}

	@RequestMapping(value = "list/action/{id_NV}.htm", params = "btnDelete")
	public String deleteVatTu(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("vattu") VatTu vatTu) {

		Integer check = this.deleteVatTu(vatTu);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa vật tư thành công!");
		} else {
			model.addAttribute("message", "Xóa có lỗi vui lòng kiểm tra lại!");
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

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/productlist";
	}

	@RequestMapping(value = "phieuxuat/{id_NV}.htm")
	public String showPhieuXuat(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("lapphieuxuat") PhieuXuat phieuXuat) {
		// Thêm một điều kiện khi quyền là nhân viên thì chỉ hiện table mã có NV đó

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu nhập
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu nhập
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		//
		model.addAttribute("btnAction", "btnAdd");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");

		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/action/{id_NV}.htm", params = "btnAdd")
	public String addPhieuXuat(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("lapphieuxuat") PhieuXuat phieuXuat) {
		Integer check = this.addPhieuXuat(phieuXuat);
		model.addAttribute("check", check);

		if (check != 0) {
			model.addAttribute("message", "Tạo phiếu xuất thành công!");
		} else {
			model.addAttribute("message", "Tạo phiếu có lỗi vui lòng kiểm tra lại!");
		}

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// List Phiếu Nhập theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu xuat
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu xuat
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");

		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/{maPX}/{id_NV}.htm", params = "lnkEdit")
	public String editPhieuXuat(ModelMap model, @PathVariable("maPX") String maPX, @PathVariable("id_NV") Integer id) {

		// Đẩy dữ liệu vào form lapphieuxuat để thực hiện update
		PhieuXuat phieuXuat = this.getPhieuXuatByMaPX(maPX);
		model.addAttribute("lapphieuxuat", phieuXuat);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Chuyển sang nút update để thực hiện chỉnh sửa phiếu nhập
		model.addAttribute("btnAction", "btnUpdate");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// List Phiếu Nhập theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu xuất theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu xuất theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");
		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/action/{id_NV}.htm", params = "btnUpdate")
	public String updatePhieuXuat(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("lapphieuxuat") PhieuXuat phieuXuat) {
		Integer check = this.updatePhieuXuat(phieuXuat);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật phiếu xuất thành công!");
		} else {
			model.addAttribute("message", "Cập nhật có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Phiếu Nhập theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu xuất theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu xuất theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");

		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/{maPX}/{id_NV}.htm", params = "lnkDel")
	public String deletePhieuXuat(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("maPX") String maPX) {

		PhieuXuat phieuXuat = this.getPhieuXuatByMaPX(maPX);
		model.addAttribute("lapphieuxuat", phieuXuat);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Chuyển sang nút update để thực hiện chỉnh sửa phiếu nhập
		model.addAttribute("btnAction", "btnDelete");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// List Phiếu NXuất theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu xuất theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu xuất theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");
		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/action/{id_NV}.htm", params = "btnDelete")
	public String deletePhieuXuat(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("lapphieuxuat") PhieuXuat phieuXuat) {
		Integer check = this.deletePhieuXuat(phieuXuat);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa phiếu xuất thành công!");
		} else {
			model.addAttribute("message", "Xóa có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Phiếu Nhập theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu xuất theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu xuất theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");
		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/chitiet/{maPX}/{id_NV}.htm")
	public String chiTietPhieuXuat(ModelMap model, @PathVariable("id_NV") Integer id, @PathVariable("maPX") String maPX,
			@ModelAttribute("chitietphieuxuat") ChiTietPhieuXuat chiTietPX) {

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Xác định mã phiếu xuất để thêm chi tiết
		model.addAttribute("maPX", maPX);

		// List Phiếu Xuaats theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu xuất theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu xuất theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Chuyển về lại btnAdd để có thể tạo chi tiết thêm nếu có
		model.addAttribute("btnAction", "btnAdd");

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietPhieuXuat> listCTPX = this.getCTPXsByMaPX(maPX);
		model.addAttribute("CTPXs", listCTPX);

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTPX");
		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/action/chitiet/{id_NV}.htm", params = "btnAdd")
	public String addCTPX(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietphieuxuat") ChiTietPhieuXuat chiTietPX) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTPX");

		Integer check = this.addCTPX(chiTietPX);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Thêm chi tiết phiếu xuất thành công!");
			// Lấy vật tư theo mã chi tiết phiếu xuất đã thêm thành công
			VatTu vatTu = this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
			vatTu.giamSoLuongVatTu(chiTietPX.getSoLuong());
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietPhieuXuat> listCTPX = this.getCTPXsByMaPX(chiTietPX.getPhieuXuat().getMaPX());
		model.addAttribute("CTPXs", listCTPX);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Phiếu Xuaats theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu nhập theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu nhập theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/chitiet/{maPX}/{maVT}/{id_NV}.htm", params = "lnkEdit")
	public String editChiTietPX(ModelMap model, @PathVariable("maPX") String maPX, @PathVariable("maVT") String maVT,
			@PathVariable("id_NV") Integer id) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTPX");

		// Đẩy dữ liệu vào form chitietphieuxuat để thực hiện update
		ChiTietPhieuXuat chiTietPX = this.getCTPX(maPX, maVT);
		model.addAttribute("chitietphieuxuat", chiTietPX);
		// Lưu số lượng hiện tại của vật tư
		soLuongHienTai = chiTietPX.getSoLuong();

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Chuyển sang nút update để thực hiện chỉnh sửa phiếu nhập
		model.addAttribute("btnAction", "btnUpdate");

		// List Phiếu Nhập theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu nhập theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu nhập theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");

		}

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietPhieuXuat> listCTPX = this.getCTPXsByMaPX(maPX);
		model.addAttribute("CTPXs", listCTPX);

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/action/chitiet/{id_NV}.htm", params = "btnUpdate")
	public String updateCTPX(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietphieuxuat") ChiTietPhieuXuat chiTietPX) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTPX");

		Integer check = this.updateCTPX(chiTietPX);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật chi tiết phiếu xuất thành công!");
			VatTu vatTu = this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
			vatTu.tangSoLuongVatTu(soLuongHienTai);
			vatTu.giamSoLuongVatTu(chiTietPX.getSoLuong());
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietPhieuXuat> listCTPX = this.getCTPXsByMaPX(chiTietPX.getPhieuXuat().getMaPX());
		model.addAttribute("CTPXs", listCTPX);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Phiếu Xuaats theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu nhập theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu nhập theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Xác định mã phiếu xuất để thêm chi tiết
		model.addAttribute("maPX", chiTietPX.getPhieuXuat().getMaPX());

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/chitiet/{maPX}/{maVT}/{id_NV}.htm", params = "lnkDel")
	public String deleteChiTietPX(ModelMap model, @PathVariable("maPX") String maPX, @PathVariable("maVT") String maVT,
			@PathVariable("id_NV") Integer id) {
		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTPX");

		// Đẩy dữ liệu vào form chitietphieuxuat để thực hiện update
		ChiTietPhieuXuat chiTietPX = this.getCTPX(maPX, maVT);
		model.addAttribute("chitietphieuxuat", chiTietPX);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Chuyển sang nút delete để thực hiện xoa' phiếu nhập
		model.addAttribute("btnAction", "btnDelete");

		// List Phiếu Nhập theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu nhập theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu nhập theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");

		}

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietPhieuXuat> listCTPX = this.getCTPXsByMaPX(maPX);
		model.addAttribute("CTPXs", listCTPX);

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		return "product/phieuxuat";
	}

	@RequestMapping(value = "phieuxuat/action/chitiet/{id_NV}.htm", params = "btnDelete")
	public String delCTPX(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietphieuxuat") ChiTietPhieuXuat chiTietPX) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTPX");

		Integer check = this.deleteCTPX(chiTietPX);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa chi tiết phiếu xuất thành công!");
			VatTu vatTu = this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
			vatTu.tangSoLuongVatTu(chiTietPX.getSoLuong());
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietPhieuXuat> listCTPX = this.getCTPXsByMaPX(chiTietPX.getPhieuXuat().getMaPX());
		model.addAttribute("CTPXs", listCTPX);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Phiếu Xuaats theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list phiếu nhập theo admin
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuats();
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list phiếu nhập theo staff
			List<PhieuXuat> listPhieuXuat = this.getPhieuXuatsByMaNV(id);
			model.addAttribute("phieuXuats", listPhieuXuat);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Xác định mã phiếu xuất để thêm chi tiết
		model.addAttribute("maPX", chiTietPX.getPhieuXuat().getMaPX());

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/phieuxuat";
	}
	
	@RequestMapping(value = "dathang/{id_NV}.htm")
	public String showDatHang(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dondathang") DatHang datHang) {
		// Thêm một điều kiện khi quyền là nhân viên thì chỉ hiện table mã có NV đó

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		//
		model.addAttribute("btnAction", "btnAdd");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");

		return "product/dathang";
	}

	@RequestMapping(value = "dathang/action/{id_NV}.htm", params = "btnAdd")
	public String addDatHang(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dondathang") DatHang datHang) {
		Integer check = this.addDatHang(datHang);
		model.addAttribute("check", check);

		if (check != 0) {
			model.addAttribute("message", "Tạo đơn đặt hàng thành công!");
		} else {
			model.addAttribute("message", "Tạo đơn có lỗi vui lòng kiểm tra lại!");
		}

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		//
		model.addAttribute("btnAction", "btnAdd");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");

		return "product/dathang";
	}

	@RequestMapping(value = "dathang/{masoDDH}/{id_NV}.htm", params = "lnkEdit")
	public String editDatHang(ModelMap model, @PathVariable("masoDDH") String masoDDH,
			@PathVariable("id_NV") Integer id) {
		// Đẩy dữ liệu vào form dondathang để thực hiện update
		DatHang datHang = this.getDatHangByMasoDDH(masoDDH);
		model.addAttribute("dondathang", datHang);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Chuyển sang nút update để thực hiện chỉnh sửa phiếu nhập
		model.addAttribute("btnAction", "btnUpdate");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Kiểm tra nếu action khi bấm vào tạo đơn đặt hàng thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");
		return "product/dathang";
	}

	@RequestMapping(value = "dathang/action/{id_NV}.htm", params = "btnUpdate")
	public String updateDatHang(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dondathang") DatHang datHang) {
		Integer check = this.updateDatHang(datHang);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật đơn đặt hàng thành công!");
		} else {
			model.addAttribute("message", "Cập nhật có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra nếu action khi bấm vào tạo đơn đặt hàng thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");

		return "product/dathang";
	}

	@RequestMapping(value = "dathang/{masoDDH}/{id_NV}.htm", params = "lnkDel")
	public String deleteDatHang(ModelMap model, @PathVariable("masoDDH") String masoDDH,
			@PathVariable("id_NV") Integer id) {

		// Đẩy dữ liệu vào form dondathang để thực hiện update
		DatHang datHang = this.getDatHangByMasoDDH(masoDDH);
		model.addAttribute("dondathang", datHang);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Chuyển sang nút update để thực hiện chỉnh sửa phiếu nhập
		model.addAttribute("btnAction", "btnDelete");

		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");
		return "product/dathang";
	}

	@RequestMapping(value = "dathang/action/{id_NV}.htm", params = "btnDelete")
	public String deleteDatHang(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("dondathang") DatHang datHang) {
		Integer check = this.deleteDatHang(datHang);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa đơn đặt hàng thành công!");
		} else {
			model.addAttribute("message", "Xóa có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");

		}
		// Lấy kho theo chi nhánh của nhân viên đăng nhập vào
		List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
		model.addAttribute("KhoByMaCN", listKho);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra nếu action khi bấm vào tạo phiếu xuất thì hiển thị ra table kho
		model.addAttribute("clickaction", "showtablekho");
		return "product/dathang";
	}

	@RequestMapping(value = "dathang/chitiet/{masoDDH}/{id_NV}.htm")
	public String chiTietDatHang(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("masoDDH") String masoDDH, @ModelAttribute("chitietdathang") ChiTietDonDatHang chiTietDDH) {

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Xác định mã đơn đặt hàng để thêm chi tiết
		model.addAttribute("masoDDH", masoDDH);

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Chuyển về lại btnAdd để có thể tạo chi tiết thêm nếu có
		model.addAttribute("btnAction", "btnAdd");

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietDonDatHang> listCTDDH = this.getCTDDHsByMaSoDDH(masoDDH);
		model.addAttribute("CTDDHs", listCTDDH);

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTDDH");
		return "product/dathang";
	}

	@RequestMapping(value = "dathang/action/chitiet/{id_NV}.htm", params = "btnAdd")
	public String addCTDDH(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietdathang") ChiTietDonDatHang chiTietDDH) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTDDH");

		Integer check = this.addCTDDH(chiTietDDH);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Thêm chi tiết đơn đặt hàng thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietDonDatHang> listCTDDH = this.getCTDDHsByMaSoDDH(chiTietDDH.getDatHang().getMasoDDH());
		model.addAttribute("CTDDHs", listCTDDH);

		// Xác định mã đơn đặt hàng để thêm chi tiết
		model.addAttribute("masoDDH", chiTietDDH.getDatHang().getMasoDDH());

		/*
		 * //Trước khi lấy list phải thực hiện phương thức trừ số lượng VatTu vatTu =
		 * this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
		 * vatTu.setSoLuongTon(vatTu.xuatVatTu(soLuong));
		 */

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/dathang";
	}

	@RequestMapping(value = "dathang/chitiet/{masoDDH}/{maVT}/{id_NV}.htm", params = "lnkEdit")
	public String editChiTietDDH(ModelMap model, @PathVariable("masoDDH") String masoDDH,
			@PathVariable("maVT") String maVT, @PathVariable("id_NV") Integer id) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTDDH");

		// Đẩy dữ liệu vào form chitietdathang để thực hiện update
		ChiTietDonDatHang chiTietDDH = this.getCTDDH(masoDDH, maVT);
		model.addAttribute("chitietdathang", chiTietDDH);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// Chuyển sang nút update để thực hiện chỉnh sửa phiếu nhập
		model.addAttribute("btnAction", "btnUpdate");

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietDonDatHang> listCTDDH = this.getCTDDHsByMaSoDDH(masoDDH);
		model.addAttribute("CTDDHs", listCTDDH);

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		return "product/dathang";
	}

	@RequestMapping(value = "dathang/action/chitiet/{id_NV}.htm", params = "btnUpdate")
	public String updateCTDDH(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietdathang") ChiTietDonDatHang chiTietDDH) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTDDH");

		Integer check = this.updateCTDDH(chiTietDDH);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật chi tiết đơn đặt hàng thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		// List chi tiết đơn đặt hàng theo mã đặt hàng
		List<ChiTietDonDatHang> listCTDDH = this.getCTDDHsByMaSoDDH(chiTietDDH.getDatHang().getMasoDDH());
		model.addAttribute("CTDDHs", listCTDDH);

		// Xác định mã đơn đặt hàng để thêm chi tiết
		model.addAttribute("masoDDH", chiTietDDH.getDatHang().getMasoDDH());

		/*
		 * //Trước khi lấy list phải thực hiện phương thức trừ số lượng VatTu vatTu =
		 * this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
		 * vatTu.setSoLuongTon(vatTu.xuatVatTu(soLuong));
		 */

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/dathang";
	}

	@RequestMapping(value = "dathang/chitiet/{masoDDH}/{maVT}/{id_NV}.htm", params = "lnkDel")
	public String deleteChiTietDDH(ModelMap model, @PathVariable("masoDDH") String masoDDH,
			@PathVariable("maVT") String maVT, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietdathang") ChiTietDonDatHang chiTietDDH) {

		// Kiểm tra action click vào chi tiết
		model.addAttribute("clickaction", "showCTDDH");

		Integer check = this.deleteCTDDH(getCTDDH(masoDDH, maVT));
		if (check != 0) {
			model.addAttribute("message", "Xóa chi tiết đơn đặt hàng thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Xác định mã phiếu xuất để thêm chi tiết
		model.addAttribute("masoDDH", masoDDH);

		// List chi tiết phiếu xuất theo mã phiếu
		List<ChiTietDonDatHang> listCTDDH = this.getCTDDHsByMaSoDDH(masoDDH);
		model.addAttribute("CTDDHs", listCTDDH);

		// Lấy list vật tư
		List<VatTu> list = this.getListVatTu();
		model.addAttribute("VatTus", list);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");

		return "product/dathang";
	}

	@RequestMapping(value = "phieunhap/{id_NV}.htm")
	public String showPhieuNhap(ModelMap model, @PathVariable("id_NV") Integer id

	) {
		// Thêm một điều kiện khi quyền là nhân viên thì chỉ hiện table mã có NV đó
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		//
		model.addAttribute("btnAction", "btnAdd");

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/{id_NV}/{masoDDH}.htm", params = "lnkAdd")
	public String showAddPhieuNhap(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("masoDDH") String masoDDH, @ModelAttribute("phieunhap") PhieuNhap phieuNhap) {
		// Thêm một điều kiện khi quyền là nhân viên thì chỉ hiện table mã có NV đó

		DatHang datHang = this.getDatHangByMasoDDH(masoDDH);
		model.addAttribute("dondathang", datHang);
		System.out.println(datHang.getKho().getTenKho());

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		//
		model.addAttribute("btnAction", "btnAdd");

		// Thêm một điều kiện khi quyền là nhân viên thì chỉ hiện table mã có NV đó
		model.addAttribute("show", "showFormAdd");
		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/action/{id_NV}.htm", params = "btnAdd")
	public String addsPhieuNhap(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("phieunhap") PhieuNhap phieuNhap) {

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());
		phieuNhap.setNhanVien(nhanVien);

		DatHang datHang = this.getDatHangByMaDDH(phieuNhap.getDatHang().getMasoDDH());
		phieuNhap.setDatHang(datHang);

		Kho kho = this.getKhoByMaKho(phieuNhap.getKho().getMaKho());
		phieuNhap.setKho(kho);

		Integer check = this.addPhieuNhap(phieuNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Tạo phiếu nhập thành công!");

		} else {
			model.addAttribute("message", "Tạo phiếu có lỗi vui lòng kiểm tra lại!");
		}
		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		model.addAttribute("show", "showFormAdd");
		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/{id_NV}/{masoDDH}.htm", params = "lnkVisit")
	public String thongTinPhieuNhap(ModelMap model, @PathVariable("id_NV") Integer id,
			@PathVariable("masoDDH") String masoDDH) {
		// Thêm một điều kiện khi quyền là nhân viên thì chỉ hiện table mã có NV đó

		DatHang datHang = this.getDatHangByMasoDDH(masoDDH);
		model.addAttribute("dondathang", datHang);
		System.out.println(datHang.getKho().getTenKho());

		PhieuNhap phieuNhap = this.getPhieuNhapByMaDDH(masoDDH);
		model.addAttribute("phieuNhapinf", phieuNhap);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// Thêm một điều kiện khi quyền là nhân viên thì chỉ hiện table mã có NV đó
		model.addAttribute("show", "showFormInfo");
		model.addAttribute("actionForm", "readonly");
		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/{masoDDH}/{id_NV}.htm", params = "lnkEdit")
	public String editPhieuNhap(ModelMap model, @ModelAttribute("phieunhap") PhieuNhap phieuNhap,
			@PathVariable("id_NV") Integer id, @PathVariable("masoDDH") String masoDDH) {

		DatHang datHang = this.getDatHangByMasoDDH(masoDDH);
		model.addAttribute("dondathang", datHang);

		PhieuNhap pn = this.getPhieuNhapByMaDDH(masoDDH);
		model.addAttribute("phieunhap", pn);
		model.addAttribute("phieuNhapinf", pn);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		//
		model.addAttribute("btnAction", "btnUpdate");
		model.addAttribute("show", "showFormInfo");
		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/action/{id_NV}.htm", params = "btnUpdate")
	public String updatePhieuNhap(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("phieunhap") PhieuNhap phieuNhap) {
		Integer check = this.updatePhieuNhap(phieuNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật phiếu nhập thành công!");
		} else {
			model.addAttribute("message", "Cập nhật có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Hiện thông tin trên bảng phiếu nhập
		PhieuNhap pn = this.getPhieuNhapByMaDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("phieuNhapinf", pn);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnUpdate");
		model.addAttribute("show", "showFormInfo");

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/{masoDDH}/{id_NV}.htm", params = "lnkDel")
	public String linkDelPhieuNhap(ModelMap model, @ModelAttribute("phieunhap") PhieuNhap phieuNhap,
			@PathVariable("id_NV") Integer id, @PathVariable("masoDDH") String masoDDH) {

		DatHang datHang = this.getDatHangByMasoDDH(masoDDH);
		model.addAttribute("dondathang", datHang);

		PhieuNhap pn = this.getPhieuNhapByMaDDH(masoDDH);
		model.addAttribute("phieunhap", pn);
		model.addAttribute("phieuNhapinf", pn);

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}
		//
		model.addAttribute("btnAction", "btnDelete");
		model.addAttribute("show", "showFormInfo");
		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/action/{id_NV}.htm", params = "btnDelete")
	public String deletePhieuNhap(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("phieunhap") PhieuNhap phieuNhap) {
		Integer check = this.deletePhieuNhap(phieuNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa phiếu nhập thành công!");
		} else {
			model.addAttribute("message", "Xóa phiếu có lỗi vui lòng kiểm tra lại!");
		}
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/chitiet/{maPN}/{id_NV}.htm")
	public String chiTietPhieuNhap(ModelMap model, @PathVariable("id_NV") Integer id, @PathVariable("maPN") String maPN,
			@ModelAttribute("chitietphieunhap") ChiTietPhieuNhap chiTietPhieuNhap) {

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		model.addAttribute("maPN", maPN);

		PhieuNhap phieuNhap = this.getPhieuNhapByMaPN(maPN);
		List<ChiTietDonDatHang> listCTDDH = this.getListCTDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("listCTDDH", listCTDDH);

		// List chi tiết phiếu nhập theo mã phiếu nhập
		List<ChiTietPhieuNhap> listCTPN = this.getListCTPNByMaPN(maPN);
		model.addAttribute("CTPNs", listCTPN);

		// Chuyển về lại btnAdd để có thể tạo chi tiết thêm nếu có
		model.addAttribute("btnAction", "btnAdd");

		// Kiểm tra action click vào chi tiết
		model.addAttribute("show", "showCTPN");
		return "product/phieunhap";
	}

	@RequestMapping("phieunhap/chitiet/changevattu/{id_NV}/{maPN}/{maVT}.htm")
	public String changeVatTu(ModelMap model, @ModelAttribute("chitietphieunhap") ChiTietPhieuNhap chiTietPhieuNhap,
			@PathVariable("maPN") String maPN, @PathVariable("id_NV") Integer id, @PathVariable("maVT") String maVT) {
		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// List chi tiết đặt hàng theo phiếu nhập
		PhieuNhap phieuNhap = this.getPhieuNhapByMaPN(maPN);
		List<ChiTietDonDatHang> listCTDDH = this.getListCTDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("listCTDDH", listCTDDH);

		// List chi tiết phiếu nhập theo mã phiếu nhập
		List<ChiTietPhieuNhap> listCTPN = this.getListCTPNByMaPN(maPN);
		model.addAttribute("CTPNs", listCTPN);

		// =====================================//
		ChiTietDonDatHang chiTietDatHang = this.getCTDDH(this.getPhieuNhapByMaPN(maPN).getDatHang().getMasoDDH(), maVT);
		model.addAttribute("chitietphieunhap", chiTietDatHang);
		/*
		 * model.addAttribute("soluong", chiTietDatHang.getSoLuong());
		 * model.addAttribute("dongia", chiTietDatHang.getDonGia());
		 */
		// =====================================//

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");
		// Kiểm tra action click vào chi tiết
		model.addAttribute("show", "showCTPN");

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/action/chitiet/{id_NV}.htm", params = "btnAdd")
	public String addCTPN(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietphieunhap") ChiTietPhieuNhap chiTietPhieuNhap) {

		Integer check = this.addCTPN(chiTietPhieuNhap);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Thêm chi tiết phiếu nhập thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		// Xác định mã đơn đặt hàng để thêm chi tiết
		model.addAttribute("maPN", chiTietPhieuNhap.getPhieuNhap().getMaPN());

		/*
		 * //Trước khi lấy list phải thực hiện phương thức trừ số lượng VatTu vatTu =
		 * this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
		 * vatTu.setSoLuongTon(vatTu.xuatVatTu(soLuong));
		 */

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// List chi tiết đặt hàng theo phiếu nhập
		PhieuNhap phieuNhap = this.getPhieuNhapByMaPN(chiTietPhieuNhap.getPhieuNhap().getMaPN());
		List<ChiTietDonDatHang> listCTDDH = this.getListCTDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("listCTDDH", listCTDDH);

		// List chi tiết phiếu nhập theo mã phiếu nhập
		List<ChiTietPhieuNhap> listCTPN = this.getListCTPNByMaPN(chiTietPhieuNhap.getPhieuNhap().getMaPN());
		model.addAttribute("CTPNs", listCTPN);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");
		// Kiểm tra action click vào chi tiết
		model.addAttribute("show", "showCTPN");

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/chitiet/{maPN}/{maVT}/{id_NV}.htm", params = "lnkEdit")
	public String editChiTietPN(ModelMap model, @PathVariable("maPN") String maPN, @PathVariable("maVT") String maVT,
			@PathVariable("id_NV") Integer id) {

		// Đẩy dữ liệu vào form chitietphieunhap để thực hiện update
		ChiTietPhieuNhap chiTietPN = this.getCTPN(maPN, maVT);
		model.addAttribute("chitietphieunhap", chiTietPN);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// List chi tiết đặt hàng theo phiếu nhập
		PhieuNhap phieuNhap = this.getPhieuNhapByMaPN(maPN);
		List<ChiTietDonDatHang> listCTDDH = this.getListCTDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("listCTDDH", listCTDDH);

		// List chi tiết phiếu nhập theo mã phiếu nhập
		List<ChiTietPhieuNhap> listCTPN = this.getListCTPNByMaPN(maPN);
		model.addAttribute("CTPNs", listCTPN);

		// Chuyển sang nút update để thực hiện chỉnh sửa phiếu nhập
		model.addAttribute("btnAction", "btnUpdate");
		// Kiểm tra action click vào chi tiết
		model.addAttribute("show", "showCTPN");

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/action/chitiet/{id_NV}.htm", params = "btnUpdate")
	public String updateCTPN(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietphieunhap") ChiTietPhieuNhap chiTietPhieuNhap) {

		Integer check = this.updateCTPN(chiTietPhieuNhap);
		if (check != 0) {
			model.addAttribute("message", "Cập nhật chi tiết phiếu nhập thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		/*
		 * //Trước khi lấy list phải thực hiện phương thức trừ số lượng VatTu vatTu =
		 * this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
		 * vatTu.setSoLuongTon(vatTu.xuatVatTu(soLuong));
		 */

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// List chi tiết đặt hàng theo phiếu nhập
		PhieuNhap phieuNhap = this.getPhieuNhapByMaPN(chiTietPhieuNhap.getPhieuNhap().getMaPN());
		List<ChiTietDonDatHang> listCTDDH = this.getListCTDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("listCTDDH", listCTDDH);

		// List chi tiết phiếu nhập theo mã phiếu nhập
		List<ChiTietPhieuNhap> listCTPN = this.getListCTPNByMaPN(chiTietPhieuNhap.getPhieuNhap().getMaPN());
		model.addAttribute("CTPNs", listCTPN);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");
		// Kiểm tra action click vào chi tiết
		model.addAttribute("show", "showCTPN");

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/chitiet/{maPN}/{maVT}/{id_NV}.htm", params = "lnkDel")
	public String deleteChiTietPN(ModelMap model, @PathVariable("maPN") String maPN, @PathVariable("maVT") String maVT,
			@PathVariable("id_NV") Integer id) {

		// Đẩy dữ liệu vào form chitietphieunhap để thực hiện update
		ChiTietPhieuNhap chiTietPN = this.getCTPN(maPN, maVT);
		model.addAttribute("chitietphieunhap", chiTietPN);

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// List chi tiết đặt hàng theo phiếu nhập
		PhieuNhap phieuNhap = this.getPhieuNhapByMaPN(maPN);
		List<ChiTietDonDatHang> listCTDDH = this.getListCTDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("listCTDDH", listCTDDH);

		// List chi tiết phiếu nhập theo mã phiếu nhập
		List<ChiTietPhieuNhap> listCTPN = this.getListCTPNByMaPN(maPN);
		model.addAttribute("CTPNs", listCTPN);

		// Chuyển sang nút delete để thực hiện xóa phiếu nhập
		model.addAttribute("btnAction", "btnDelete");
		// Kiểm tra action click vào chi tiết
		model.addAttribute("show", "showCTPN");

		return "product/phieunhap";
	}

	@RequestMapping(value = "phieunhap/action/chitiet/{id_NV}.htm", params = "btnDelete")
	public String deleteCTPN(ModelMap model, @PathVariable("id_NV") Integer id,
			@ModelAttribute("chitietphieunhap") ChiTietPhieuNhap chiTietPhieuNhap) {

		Integer check = this.deleteCTPN(chiTietPhieuNhap);
		if (check != 0) {
			model.addAttribute("message", "Xóa chi tiết phiếu nhập thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		/*
		 * //Trước khi lấy list phải thực hiện phương thức trừ số lượng VatTu vatTu =
		 * this.getVatTuByMaVT(chiTietPX.getVatTu().getMaVT());
		 * vatTu.setSoLuongTon(vatTu.xuatVatTu(soLuong));
		 */

		// Lấy dữ liệu của Nhân viên để đẩy lên thông tin của left-sidenav và topbar
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Đặt hàng theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangs();
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "admin");
		} else {
			// Lấy list đặt hàng
			List<DatHang> listDatHang = this.getDatHangsByMaNV(id);
			model.addAttribute("datHangs", listDatHang);
			model.addAttribute("phanQuyen", "nhanvien");
		}

		// List chi tiết đặt hàng theo phiếu nhập
		PhieuNhap phieuNhap = this.getPhieuNhapByMaPN(chiTietPhieuNhap.getPhieuNhap().getMaPN());
		List<ChiTietDonDatHang> listCTDDH = this.getListCTDDH(phieuNhap.getDatHang().getMasoDDH());
		model.addAttribute("listCTDDH", listCTDDH);

		// List chi tiết phiếu nhập theo mã phiếu nhập
		List<ChiTietPhieuNhap> listCTPN = this.getListCTPNByMaPN(chiTietPhieuNhap.getPhieuNhap().getMaPN());
		model.addAttribute("CTPNs", listCTPN);

		// Chuyển về lại btnAdd để có thể tạo phiếu tiếp nếu có
		model.addAttribute("btnAction", "btnAdd");
		// Kiểm tra action click vào chi tiết
		model.addAttribute("show", "showCTPN");

		return "product/phieunhap";
	}

	@RequestMapping(value = "kho/{id_NV}.htm", method = RequestMethod.GET)
	public String showKhoProduct(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("kho") Kho kho) {
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Kho theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			List<Kho> listKho = this.getKhos();
			model.addAttribute("quyen", "admin");
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanhs", this.getChiNhanhs());
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanh", nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("quyen", "nhanvien");
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Chuyển sang chế độ add
		model.addAttribute("btnAction", "btnAdd");

		return "product/kho";
	}

	@RequestMapping(value = "kho/action/{id_NV}.htm", params = "btnAdd")
	public String addKhoProduct(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("kho") Kho kho) {

		Integer check = this.addKho(kho);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Thêm kho thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Kho theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			List<Kho> listKho = this.getKhos();
			model.addAttribute("quyen", "admin");
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanhs", this.getChiNhanhs());
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanh", nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("quyen", "nhanvien");
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Chuyển sang chế độ add
		model.addAttribute("btnAction", "btnAdd");
		return "product/kho";
	}

	@RequestMapping(value = "kho/{maKho}/{id_NV}.htm", params = "lnkEdit")
	public String editKhoProduct(ModelMap model, @PathVariable("maKho") String maKho,
			@PathVariable("id_NV") Integer id) {

		model.addAttribute("kho", this.getKhoByMaKho(maKho));

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Kho theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			List<Kho> listKho = this.getKhos();
			model.addAttribute("quyen", "admin");
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanhs", this.getChiNhanhs());
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanh", nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("quyen", "nhanvien");
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Chuyển sang chế độ add
		model.addAttribute("btnAction", "btnUpdate");

		return "product/kho";
	}

	@RequestMapping(value = "kho/action/{id_NV}.htm", params = "btnUpdate")
	public String updateKhoProduct(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("kho") Kho kho) {

		Integer check = this.updateKho(kho);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Sửa thông tin kho thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Kho theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			List<Kho> listKho = this.getKhos();
			model.addAttribute("quyen", "admin");
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanhs", this.getChiNhanhs());
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanh", nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("quyen", "nhanvien");
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Chuyển sang chế độ add
		model.addAttribute("btnAction", "btnAdd");
		return "product/kho";
	}

	@RequestMapping(value = "kho/{maKho}/{id_NV}.htm", params = "lnkDel")
	public String delKhoProduct(ModelMap model, @PathVariable("maKho") String maKho,
			@PathVariable("id_NV") Integer id) {

		model.addAttribute("kho", this.getKhoByMaKho(maKho));

		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Kho theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			List<Kho> listKho = this.getKhos();
			model.addAttribute("quyen", "admin");
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanhs", this.getChiNhanhs());
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanh", nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("quyen", "nhanvien");
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Chuyển sang chế độ add
		model.addAttribute("btnAction", "btnDelete");

		return "product/kho";
	}

	@RequestMapping(value = "kho/action/{id_NV}.htm", params = "btnDelete")
	public String deleteKhoProduct(ModelMap model, @PathVariable("id_NV") Integer id, @ModelAttribute("kho") Kho kho) {

		Integer check = this.deleteKho(kho);
		model.addAttribute("check", check);
		if (check != 0) {
			model.addAttribute("message", "Xóa thông tin kho thành công!");
		} else {
			model.addAttribute("message", "Đã có lỗi vui lòng kiểm tra lại!");
		}
		NhanVien nhanVien = this.getNhanVienByID(id);
		model.addAttribute("id_NV", id);
		model.addAttribute("hoTen", nhanVien.getHo() + " " + nhanVien.getTen());

		// List Kho theo quyền
		if (nhanVien.getPhanQuyen().getIdPhanQuyen() == 1)// admin
		{
			List<Kho> listKho = this.getKhos();
			model.addAttribute("quyen", "admin");
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanhs", this.getChiNhanhs());
			model.addAttribute("phanQuyen", "admin");
		} else {
			List<Kho> listKho = this.getKhoByCN(nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("khos", listKho);
			model.addAttribute("chiNhanh", nhanVien.getChiNhanh().getMaCN());
			model.addAttribute("quyen", "nhanvien");
			model.addAttribute("phanQuyen", "nhanvien");
		}
		// Chuyển sang chế độ add
		model.addAttribute("btnAction", "btnAdd");
		return "product/kho";
	}

	public List<ChiNhanh> getChiNhanhs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiNhanh";
		Query query = session.createQuery(hql);

		List<ChiNhanh> list = query.list();
		return list;
	}

	public List<Kho> getKhos() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Kho";
		Query query = session.createQuery(hql);

		List<Kho> list = query.list();
		return list;
	}

	public NhanVien getNhanVienByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien n WHERE n.maNV= :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);

		NhanVien nhanVien = (NhanVien) query.list().get(0);
		return nhanVien;
	}

	public List<PhieuXuat> getPhieuXuats() {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuXuat";
		Query query = session.createQuery(hql);

		List<PhieuXuat> list = query.list();
		return list;
	}

	public List<PhieuXuat> getPhieuXuatsByMaNV(Integer maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuXuat WHERE nhanVien.maNV= :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", maNV);

		List<PhieuXuat> list = query.list();
		return list;
	}

	public List<Kho> getKhoByCN(String maCN) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Kho k WHERE k.chiNhanh.maCN= :maCN";
		Query query = session.createQuery(hql);
		query.setParameter("maCN", maCN);

		List<Kho> list = query.list();
		return list;
	}

	public PhieuXuat getPhieuXuatByMaPX(String maPX) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuXuat WHERE maPX= :maPX";
		Query query = session.createQuery(hql);
		query.setParameter("maPX", maPX);

		PhieuXuat phieuXuat = (PhieuXuat) query.list().get(0);
		return phieuXuat;
	}

	public Integer addPhieuXuat(PhieuXuat phieuXuat) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(phieuXuat);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer updatePhieuXuat(PhieuXuat phieuXuat) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(phieuXuat);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer deletePhieuXuat(PhieuXuat phieuXuat) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(phieuXuat);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public List<VatTu> getListVatTu() {
		Session session = factory.getCurrentSession();
		String hql = "FROM VatTu";
		Query query = session.createQuery(hql);

		List<VatTu> list = query.list();
		return list;
	}

	public VatTu getVatTuByMaVT(String maVT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM VatTu WHERE maVT= :maVT";
		Query query = session.createQuery(hql);
		query.setParameter("maVT", maVT);

		VatTu vatTu = (VatTu) query.list().get(0);
		return vatTu;
	}

	public Integer addVatTu(VatTu vatTu) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(vatTu);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer updateVatTu(VatTu vatTu) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(vatTu);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer deleteVatTu(VatTu vatTu) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(vatTu);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public List<ChiTietPhieuXuat> getCTPXsByMaPX(String maPX) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiTietPhieuXuat ct WHERE ct.phieuXuat.maPX= :maPX";
		Query query = session.createQuery(hql);
		query.setParameter("maPX", maPX);

		List<ChiTietPhieuXuat> list = query.list();
		return list;
	}

	public ChiTietPhieuXuat getCTPX(String maPX, String maVT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiTietPhieuXuat ct WHERE ct.phieuXuat.maPX= :maPX AND ct.vatTu.maVT= :maVT";
		Query query = session.createQuery(hql);
		query.setParameter("maPX", maPX);
		query.setParameter("maVT", maVT);

		ChiTietPhieuXuat list = (ChiTietPhieuXuat) query.list().get(0);
		return list;
	}

	public Integer addCTPX(ChiTietPhieuXuat chiTietPX) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(chiTietPX);
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

	public Integer updateCTPX(ChiTietPhieuXuat chiTietPX) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(chiTietPX);
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

	public Integer deleteCTPX(ChiTietPhieuXuat chiTietPX) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(chiTietPX);
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

	public Integer addDatHang(DatHang datHang) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(datHang);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer updateDatHang(DatHang datHang) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(datHang);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	public Integer deleteDatHang(DatHang datHang) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(datHang);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {

			session.close();
		}
		return 1;
	}

	public DatHang getDatHangByMasoDDH(String masoDDH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DatHang WHERE masoDDH= :masoDDH";
		Query query = session.createQuery(hql);
		query.setParameter("masoDDH", masoDDH);

		DatHang datHang = (DatHang) query.list().get(0);
		return datHang;
	}

	public List<DatHang> getDatHangs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM DatHang";
		Query query = session.createQuery(hql);

		List<DatHang> list = query.list();
		return list;
	}

	public List<DatHang> getDatHangsByMaNV(Integer maNV) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DatHang WHERE nhanVien.maNV= :maNV";
		Query query = session.createQuery(hql);
		query.setParameter("maNV", maNV);

		List<DatHang> list = query.list();
		return list;
	}

	public List<ChiTietDonDatHang> getCTDDHsByMaSoDDH(String masoDDH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiTietDonDatHang ct WHERE ct.datHang.masoDDH= :masoDDH";
		Query query = session.createQuery(hql);
		query.setParameter("masoDDH", masoDDH);

		List<ChiTietDonDatHang> list = query.list();
		return list;
	}

	public ChiTietDonDatHang getCTDDH(String masoDDH, String maVT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiTietDonDatHang ct WHERE ct.datHang.masoDDH= :masoDDH AND ct.vatTu.maVT= :maVT";
		Query query = session.createQuery(hql);
		query.setParameter("masoDDH", masoDDH);
		query.setParameter("maVT", maVT);

		ChiTietDonDatHang datHang = (ChiTietDonDatHang) query.list().get(0);
		return datHang;
	}

	public Integer addCTDDH(ChiTietDonDatHang chiTietDDH) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(chiTietDDH);
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

	public Integer updateCTDDH(ChiTietDonDatHang chiTietDDH) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(chiTietDDH);
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

	public Integer deleteCTDDH(ChiTietDonDatHang chiTietDDH) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(chiTietDDH);
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

	public Integer addPhieuNhap(PhieuNhap phieuNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(phieuNhap);
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

	public Integer updatePhieuNhap(PhieuNhap phieuNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(phieuNhap);
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

	public Integer deletePhieuNhap(PhieuNhap phieuNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(phieuNhap);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			return 0;
		} finally {

			session.close();
		}
		return 1;
	}

	public Kho getKhoByMaKho(String maKho) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Kho WHERE maKho= :maKho";
		Query query = session.createQuery(hql);
		query.setParameter("maKho", maKho);

		Kho kho = (Kho) query.list().get(0);
		return kho;
	}

	public DatHang getDatHangByMaDDH(String maDDH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DatHang WHERE masoDDH= :maDDH";
		Query query = session.createQuery(hql);
		query.setParameter("maDDH", maDDH);

		DatHang datHang = (DatHang) query.list().get(0);
		return datHang;
	}

	public PhieuNhap getPhieuNhapByMaDDH(String maDDH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhap WHERE datHang.masoDDH= :maDDH";
		Query query = session.createQuery(hql);
		query.setParameter("maDDH", maDDH);

		PhieuNhap phieuNhap = (PhieuNhap) query.list().get(0);
		return phieuNhap;
	}

	public PhieuNhap getPhieuNhapByMaPN(String maPN) {
		Session session = factory.getCurrentSession();
		String hql = "FROM PhieuNhap WHERE maPN= :maPN";
		Query query = session.createQuery(hql);
		query.setParameter("maPN", maPN);

		PhieuNhap phieuNhap = (PhieuNhap) query.list().get(0);
		return phieuNhap;
	}

	public List<ChiTietDonDatHang> getListCTDDH(String maDDH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiTietDonDatHang WHERE datHang.masoDDH= :maDDH";
		Query query = session.createQuery(hql);
		query.setParameter("maDDH", maDDH);

		List<ChiTietDonDatHang> list = query.list();
		return list;
	}

	public List<VatTu> getListVatTuByMaDDH(String maDDH) {
		Session session = factory.getCurrentSession();
		String hql = "FROM VatTu WHERE datHang.masoDDH= :maDDH";
		Query query = session.createQuery(hql);
		query.setParameter("maDDH", maDDH);

		List<VatTu> list = query.list();
		return list;
	}

	public Integer addCTPN(ChiTietPhieuNhap chiTietPhieuNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(chiTietPhieuNhap);
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

	public Integer updateCTPN(ChiTietPhieuNhap chiTietPhieuNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(chiTietPhieuNhap);
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

	public Integer deleteCTPN(ChiTietPhieuNhap chiTietPhieuNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(chiTietPhieuNhap);
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

	public List<ChiTietPhieuNhap> getListCTPNByMaPN(String maPN) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiTietPhieuNhap WHERE phieuNhap.maPN= :maPN";
		Query query = session.createQuery(hql);
		query.setParameter("maPN", maPN);

		List<ChiTietPhieuNhap> list = query.list();
		return list;
	}

	public ChiTietPhieuNhap getCTPN(String maPN, String maVT) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ChiTietPhieuNhap ct WHERE ct.phieuNhap.maPN= :maPN AND ct.vatTu.maVT= :maVT";
		Query query = session.createQuery(hql);
		query.setParameter("maPN", maPN);
		query.setParameter("maVT", maVT);

		ChiTietPhieuNhap phieuNhap = (ChiTietPhieuNhap) query.list().get(0);
		return phieuNhap;
	}

	public Integer addKho(Kho kho) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(kho);
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

	public Integer updateKho(Kho kho) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(kho);
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

	public Integer deleteKho(Kho kho) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.delete(kho);
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
