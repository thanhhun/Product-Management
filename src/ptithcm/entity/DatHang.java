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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "DatHang")
public class DatHang {

	@Id
	@Column(name = "MasoDDH")
	private String masoDDH;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Column(name = "NGAY")
	private Date ngay;

	@Column(name = "NhaCC")
	private String nhaCC;

	@ManyToOne
	@JoinColumn(name = "MANV")
	private NhanVien nhanVien;

	@ManyToOne
	@JoinColumn(name = "MAKHO")
	private Kho kho;

	@OneToOne(mappedBy = "datHang")
	private PhieuNhap phieuNhap;

	@OneToMany(mappedBy = "datHang", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<ChiTietDonDatHang> chiTietDonDatHangs;

	public String getMasoDDH() {
		return masoDDH;
	}

	public void setMasoDDH(String masoDDH) {
		this.masoDDH = masoDDH;
	}

	public Date getNgay() {
		return ngay;
	}

	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}

	public String getNhaCC() {
		return nhaCC;
	}

	public void setNhaCC(String nhaCC) {
		this.nhaCC = nhaCC;
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

	public PhieuNhap getPhieuNhap() {
		return phieuNhap;
	}

	public void setPhieuNhap(PhieuNhap phieuNhap) {
		this.phieuNhap = phieuNhap;
	}

	public Set<ChiTietDonDatHang> getChiTietDonDatHangs() {
		return chiTietDonDatHangs;
	}

	public void setChiTietDonDatHangs(Set<ChiTietDonDatHang> chiTietDonDatHangs) {
		this.chiTietDonDatHangs = chiTietDonDatHangs;
	}

}