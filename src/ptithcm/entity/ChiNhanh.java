package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ChiNhanh")
public class ChiNhanh {
	@Id
	@Column(name = "MACN")
	private String maCN;
	@Column(name = "ChiNhanh")
	private String tenCN;
	@Column(name = "DIACHI")
	private String diaChi;
	@Column(name = "SoDT")
	private String sdt;

	@OneToMany(mappedBy = "chiNhanh", fetch = FetchType.EAGER)
	private Collection<NhanVien> nhanVien;
	@OneToMany(mappedBy = "chiNhanh", fetch = FetchType.EAGER)
	private Collection<Kho> kho;

	public String getMaCN() {
		return maCN;
	}

	public void setMaCN(String maCN) {
		this.maCN = maCN;
	}

	public String getTenCN() {
		return tenCN;
	}

	public void setTenCN(String tenCN) {
		this.tenCN = tenCN;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public Collection<NhanVien> getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(Collection<NhanVien> nhanVien) {
		this.nhanVien = nhanVien;
	}

	public Collection<Kho> getKho() {
		return kho;
	}

	public void setKho(Collection<Kho> kho) {
		this.kho = kho;
	}

}
