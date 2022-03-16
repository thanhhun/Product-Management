package ptithcm.controller;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.bean.Mailer;
import ptithcm.entity.DangNhap;
import ptithcm.entity.NhanVien;
import ptithcm.entity.PhieuNhap;

@Transactional
@Controller
@RequestMapping("/login/")
public class LoginController {

	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String showLogin(ModelMap map, @ModelAttribute("account") DangNhap dangNhap) {
		
		return "login/form-dangnhap";
	}

	@RequestMapping(value = "action", method = RequestMethod.POST)
	public String showIndex(ModelMap model, @ModelAttribute("account") DangNhap dangNhap, HttpSession ss) {
		try {
			dangNhap = this.getDangNhap(dangNhap.getNhanVien().getMaNV(), dangNhap.getMatKhau());

			// Trả về id_NV và họ tên cho left-sidenav và top-bar vì dùng chung
			model.addAttribute("id_NV", dangNhap.getNhanVien().getMaNV());
			model.addAttribute("hoTen", dangNhap.getNhanVien().getHo() + " " + dangNhap.getNhanVien().getTen());
			model.addAttribute("user", dangNhap);
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhap.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			} else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			}
			// Trả về cho interceptor thực hiện
			ss.setAttribute("DangNhap", "user");
			return "user/index";
		} 
		catch (Exception e) {
			// Không lấy được dữ liệu vào đăng nhập
			model.addAttribute("message", "Đăng nhập không thành công!");

			// Thông báo thất bại
			model.addAttribute("check", 0);
			return "login/form-dangnhap";
		}
	}

	public DangNhap getDangNhap(Integer id, String matKhau) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DangNhap d WHERE d.nhanVien.maNV= :id AND d.matKhau= :matKhau";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		query.setParameter("matKhau", matKhau);
		DangNhap dangNhap = (DangNhap) query.list().get(0);
		return dangNhap;
	}

	@RequestMapping(value = "lock/{id_NV}.htm")
	public String lockLogin(ModelMap model, @PathVariable("id_NV") Integer id) {
		DangNhap dangNhap = this.getDangNhapByID(id);
		model.addAttribute("accountLock", dangNhap);
		model.addAttribute("Ten", dangNhap.getNhanVien().getTen());
		return "login/form-lock";
	}

	public DangNhap getDangNhapByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM DangNhap d WHERE d.nhanVien.maNV= :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		DangNhap dangNhap = (DangNhap) query.list().get(0);
		return dangNhap;
	}

	@RequestMapping(value = "unlock/{id_NV}.htm", method = RequestMethod.POST)
	public String unlock(ModelMap model, @PathVariable("id_NV") Integer id, @RequestParam("password") String pw) {
		DangNhap dangNhap = this.getDangNhapByID(id);

		System.out.println(dangNhap.getMatKhau());

		if (dangNhap.getMatKhau().equals(pw)) {
			model.addAttribute("hoTen", dangNhap.getNhanVien().getHo() + " " + dangNhap.getNhanVien().getTen());
			model.addAttribute("id_NV", dangNhap.getNhanVien().getMaNV());
			model.addAttribute("user", dangNhap);
			// Kiểm tra đăng nhập theo quyền nào
			if (dangNhap.getNhanVien().getPhanQuyen().getIdPhanQuyen() == 1) // 1 là quyền Admin
			{
				model.addAttribute("phanQuyen", "admin");
				System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			} else {
				model.addAttribute("phanQuyen", "nhanvien");
				System.out.println(dangNhap.getNhanVien().getPhanQuyen().getTenPhanQuyen());
			}
			return "user/index";
		} else {
			model.addAttribute("message", "Sai mật khẩu, vui lòng thử lại!");
		}
		model.addAttribute("Ten", dangNhap.getNhanVien().getTen());
		return "login/form-lock";
	}

	@RequestMapping(value = "forgotpw", method = RequestMethod.GET)
	public String showFormForgotpw(ModelMap model) {
		model.addAttribute("show", "sendCode");
		return "login/forgotpw";
	}

	@Autowired
	JavaMailSender mailer;
	public static String checkCode;

	@RequestMapping(value = "sendcode", method = RequestMethod.POST)
	public String send(ModelMap model, @RequestParam("id") String id, @RequestParam("email") String email) {
		//Bắt điều kiện nhập tài khoản sai với quy định
		Integer userId;
		try {
			userId = Integer.parseInt(id);
		} catch (Exception e) {
			model.addAttribute("message","Tài khoản không tồn tại !");
			model.addAttribute("show", "sendCode");
			return "login/forgotpw";
		}
		//Kiểm tra username và email của nhân viên đó
		NhanVien nhanVien = this.getNhanVienByID(userId);
		if (!nhanVien.getEmail().equals(email)) {
			model.addAttribute("message", "Tài khoản và Email không trùng nhau");
			model.addAttribute("show", "sendCode");
			return "login/forgotpw";
		}
		//Thực hiện gửi mã xác nhận
		// Random VerifyCode
		checkCode = RandomStringUtils.randomAlphanumeric(9);
		String body = "Your confirm code: " + checkCode;
		String subject = "Validation Code";
		try {
			// Create mail
			MimeMessage mail = mailer.createMimeMessage();
			// Use class Helper
			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setFrom("Admin", "Admin");
			helper.setTo("Undisclosed Recipients"+"<"+email+">");
			helper.setSubject(subject);
			helper.setText(body, true);
			// Send mail
			System.out.println(mailer.toString());
			mailer.send(mail);
			model.addAttribute("message", "Gửi mã xác nhận thành công!");
			model.addAttribute("show", "confirmCode");
			model.addAttribute("check", "success");

			// Xác nhận username thay đổi mật khẩu
			model.addAttribute("id_NV", userId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("message", "Send code fail");
			model.addAttribute("show", "sendCode");
		}
		return "login/forgotpw";
	}

	@RequestMapping(value = "confirmcode/{id_NV}.htm", method=RequestMethod.POST)
	public String confirm(ModelMap model,
			@PathVariable("id_NV") Integer id,
			@RequestParam("confirmCode") String confirmCode) {
		//Bắt điều kiện khoảng trắng
		confirmCode=confirmCode.trim();
		System.out.println(confirmCode);
		
		if (confirmCode.equals(checkCode)) {
			model.addAttribute("show", "newPassword");
			model.addAttribute("id_NV",id);
		} else {
			model.addAttribute("message", "Mã xác nhận không hợp lệ!");
			model.addAttribute("show", "confirmCode");
		}
		return "login/forgotpw";
	}

	@RequestMapping(value = "newpw/{id_NV}.htm", method=RequestMethod.POST)
	public String newpw(ModelMap model, @PathVariable("id_NV") Integer id, @RequestParam("newpw") String newpw,
			@RequestParam("confirmpw") String confirmpw) {
		
		if (newpw.equals(confirmpw)) {
			DangNhap dangNhap = this.getDangNhapByID(id);
			dangNhap.setMatKhau(newpw);
			Integer update = this.updateDangNhap(dangNhap);
			model.addAttribute("message", "Mật khẩu đã được thay đổi.");
			model.addAttribute("check","success");
		} else {
			model.addAttribute("message", "Hai mật khẩu không trùng nhau! Vui lòng thử lại!");
			model.addAttribute("check","fail");
		}
		model.addAttribute("show", "newPassword");
		return "login/forgotpw";
	}

	@RequestMapping("exit")
	public String logout(HttpSession ss) {
		ss.removeAttribute("DangNhap");
		return "redirect:/login/index.htm";
	}

	public NhanVien getNhanVienByID(Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NhanVien n WHERE n.maNV= :id";
		Query quey = session.createQuery(hql);
		quey.setParameter("id", id);

		NhanVien nhanVien = (NhanVien) quey.list().get(0);
		return nhanVien;
	}

	public Integer updateDangNhap(DangNhap dangNhap) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(dangNhap);
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
