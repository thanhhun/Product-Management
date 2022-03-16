package ptithcm.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Vattu")
public class VatTu {

	@Id
	@Column(name = "MAVT")
	private String maVT;

	@Column(name = "TENVT")
	private String tenVT;

	@Column(name = "DVT")
	private String donVi;

	@Column(name = "SOLUONGTON")
	private Integer soLuongTon;

	@Column(name = "PHOTO")
	private String phoTo;

	@OneToMany(mappedBy = "vatTu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ChiTietPhieuXuat> chiTietPhieuXuats;

	@OneToMany(mappedBy = "vatTu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ChiTietPhieuNhap> chiTietPhieuNhaps;

	@OneToMany(mappedBy = "vatTu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ChiTietDonDatHang> chiTietDonDatHangs;

	public String getMaVT() {
		return maVT;
	}

	public void setMaVT(String maVT) {
		this.maVT = maVT;
	}

	public String getTenVT() {
		return tenVT;
	}

	public void setTenVT(String tenVT) {
		this.tenVT = tenVT;
	}

	public String getDonVi() {
		return donVi;
	}

	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}

	public Integer getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(Integer soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	public String getPhoTo() {
		return phoTo;
	}

	public void setPhoTo(String phoTo) {
		this.phoTo = phoTo;
	}

	public Set<ChiTietPhieuXuat> getChiTietPhieuXuats() {
		return chiTietPhieuXuats;
	}

	public void setChiTietPhieuXuats(Set<ChiTietPhieuXuat> chiTietPhieuXuats) {
		this.chiTietPhieuXuats = chiTietPhieuXuats;
	}

	public Set<ChiTietPhieuNhap> getChiTietPhieuNhaps() {
		return chiTietPhieuNhaps;
	}

	public void setChiTietPhieuNhaps(Set<ChiTietPhieuNhap> chiTietPhieuNhaps) {
		this.chiTietPhieuNhaps = chiTietPhieuNhaps;
	}

	public Set<ChiTietDonDatHang> getChiTietDonDatHangs() {
		return chiTietDonDatHangs;
	}

	public void setChiTietDonDatHangs(Set<ChiTietDonDatHang> chiTietDonDatHangs) {
		this.chiTietDonDatHangs = chiTietDonDatHangs;
	}

	/* Phương Thức */
	public void giamSoLuongVatTu(Integer soLuong) {
		this.setSoLuongTon(this.soLuongTon - soLuong);
	}

	public void tangSoLuongVatTu(Integer soLuong) {
		this.setSoLuongTon(this.soLuongTon + soLuong);
	}
}
