package ptithcm.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Kho")
public class Kho {
	@Id
	@Column(name="MAKHO")
	private String maKho;
	@Column(name="TENKHO")
	private String tenKho;
	@Column(name="DIACHI")
	private String diaChi;
	
	@ManyToOne
	@JoinColumn(name="MACN")
	private ChiNhanh chiNhanh;

	@OneToMany(mappedBy = "kho", fetch = FetchType.EAGER)
	private Set<PhieuNhap> phieuNhaps;
	
	@OneToMany(mappedBy = "kho", fetch = FetchType.EAGER)
	private Set<PhieuXuat> phieuXuats;
	
	@OneToMany(mappedBy = "kho", fetch = FetchType.EAGER)
	private Set<DatHang> datHangs;
	
	
	public String getMaKho() {
		return maKho;
	}

	public void setMaKho(String maKho) {
		this.maKho = maKho;
	}

	public String getTenKho() {
		return tenKho;
	}

	public void setTenKho(String tenKho) {
		this.tenKho = tenKho;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public ChiNhanh getChiNhanh() {
		return chiNhanh;
	}

	public void setChiNhanh(ChiNhanh chiNhanh) {
		this.chiNhanh = chiNhanh;
	}

	public Set<PhieuNhap> getPhieuNhaps() {
		return phieuNhaps;
	}

	public void setPhieuNhaps(Set<PhieuNhap> phieuNhaps) {
		this.phieuNhaps = phieuNhaps;
	}

	public Set<PhieuXuat> getPhieuXuats() {
		return phieuXuats;
	}

	public void setPhieuXuats(Set<PhieuXuat> phieuXuats) {
		this.phieuXuats = phieuXuats;
	}

	public Set<DatHang> getDatHangs() {
		return datHangs;
	}

	public void setDatHangs(Set<DatHang> datHangs) {
		this.datHangs = datHangs;
	}
	
	
}
