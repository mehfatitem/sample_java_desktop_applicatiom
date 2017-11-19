package sample_dp_1.Enums;

public enum UserMessage {
	UserExist {
		public String toString() {
			return "Bu kullan�c� sistemde mevcuttur.";
		}
	},

	EmptyArea {
		public String toString() {
			return "Bo� alan b�rakmay�n.";
		}
	},
	RecordIsSuccess {
		public String toString() {
			return "Kay�t ba�ar�yla eklenmi�tir.";
		}
	},
	ThereIsNoRecord {
		public String toString() {
			return "Sistemde kay�t mevcut de�ildir.";
		}
	},
	UpdateIsSuccess {
		public String toString() {
			return "Kay�t ba�ar�yla g�ncellenmi�tir.";
		}
	},
	UpdateIsFailed {
		public String toString() {
			return "G�ncelleme i�lemi ba�ar�s�z.";
		}
	},
	DeleteIsSuccess {
		public String toString() {
			return "Kay�t ba�ar�yla silinmi�ir.";
		}
	},
	NamePlaceHolder {
		public String toString() {
			return "Ad�n�z� giriniz...";
		}
	},
	SurnamePlaceHolder {
		public String toString() {
			return "Soyad�n�z� giriniz...";
		}
	}
}