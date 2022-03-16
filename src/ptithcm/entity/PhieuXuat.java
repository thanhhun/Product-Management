package ptithcm.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PhieuXuat")
public class PhieuXuat {

	@Id
	@Column(name = "MAPX")
	private String maPX;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "NGAY")
	private Date ngay;

	@Column(name = "HOTENKH")
	private String hoTenKH;

	@ManyToOne
	@JoinColumn(name = "MANV")
	private NhanVien nhanVien;

	@ManyToOne
	@JoinColumn(name = "MAKHO")
	private Kho kho;

	@OneToMany(mappedBy = "phieuXuat", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ChiTietPhieuXuat> chiTietPhieuXuats;

	public String getMaPX() {
		return maPX;
	}

	public void setMaPX(String maPX) {
		this.maPX = maPX;
	}

	public Date getNgay() {
		return ngay;
	}

	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}

	public String getHoTenKH() {
		return hoTenKH;
	}

	public void setHoTenKH(String hoTenKH) {
		this.hoTenKH = hoTenKH;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	
	public Kho getKho() {
		return kho;
	}

	public void setKho(Kho kho) {
		this.kho = kho;
	}

	public Set<ChiTietPhieuXuat> getChiTietPhieuXuats() {
		return chiTietPhieuXuats;
	}

	public void setChiTietPhieuXuats(Set<ChiTietPhieuXuat> chiTietPhieuXuats) {
		this.chiTietPhieuXuats = chiTietPhieuXuats;
	}

	

}
