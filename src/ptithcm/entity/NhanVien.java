package ptithcm.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "NhanVien")
public class NhanVien {

	@Id
	@Column(name = "MANV")
	private Integer maNV;

	@Column(name = "HO")
	private String ho;

	@Column(name = "TEN")
	private String ten;

	@Column(name = "DIACHI")
	private String diaChi;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "NGAYSINH")
	private Date ngaySinh;

	@Column(name = "SDT")
	private String soDT;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "LUONG")
	private Float luong;

	@OneToOne(mappedBy = "nhanVien")
	private DangNhap dangNhap;

	@ManyToOne
	@JoinColumn(name = "MACN")
	private ChiNhanh chiNhanh;

	@ManyToOne
	@JoinColumn(name = "Id_PhanQuyen")
	private PhanQuyen phanQuyen;

	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
	private Set<PhieuXuat> phieuXuats;

	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
	private Set<PhieuXuat> phieuNhaps;

	@OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
	private Set<DatHang> datHangs;

	public Integer getMaNV() {
		return maNV;
	}

	public void setMaNV(Integer maNV) {
		this.maNV = maNV;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getLuong() {
		return luong;
	}

	public void setLuong(Float luong) {
		this.luong = luong;
	}

	public DangNhap getDangNhap() {
		return dangNhap;
	}

	public void setDangNhap(DangNhap dangNhap) {
		this.dangNhap = dangNhap;
	}

	public ChiNhanh getChiNhanh() {
		return chiNhanh;
	}

	public void setChiNhanh(ChiNhanh chiNhanh) {
		this.chiNhanh = chiNhanh;
	}

	public PhanQuyen getPhanQuyen() {
		return phanQuyen;
	}

	public void setPhanQuyen(PhanQuyen phanQuyen) {
		this.phanQuyen = phanQuyen;
	}

	public Set<PhieuXuat> getPhieuXuats() {
		return phieuXuats;
	}

	public void setPhieuXuats(Set<PhieuXuat> phieuXuats) {
		this.phieuXuats = phieuXuats;
	}

	public Set<PhieuXuat> getPhieuNhaps() {
		return phieuNhaps;
	}

	public void setPhieuNhaps(Set<PhieuXuat> phieuNhaps) {
		this.phieuNhaps = phieuNhaps;
	}

	public Set<DatHang> getDatHangs() {
		return datHangs;
	}

	public void setDatHangs(Set<DatHang> datHangs) {
		this.datHangs = datHangs;
	}

	

}
