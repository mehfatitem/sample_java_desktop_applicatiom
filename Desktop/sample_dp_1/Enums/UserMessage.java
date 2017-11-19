package sample_dp_1.Enums;

public enum UserMessage {
	UserExist {
		public String toString() {
			return "Bu kullanýcý sistemde mevcuttur.";
		}
	},

	EmptyArea {
		public String toString() {
			return "Boþ alan býrakmayýn.";
		}
	},
	RecordIsSuccess {
		public String toString() {
			return "Kayýt baþarýyla eklenmiþtir.";
		}
	},
	ThereIsNoRecord {
		public String toString() {
			return "Sistemde kayýt mevcut deðildir.";
		}
	},
	UpdateIsSuccess {
		public String toString() {
			return "Kayýt baþarýyla güncellenmiþtir.";
		}
	},
	UpdateIsFailed {
		public String toString() {
			return "Güncelleme iþlemi baþarýsýz.";
		}
	},
	DeleteIsSuccess {
		public String toString() {
			return "Kayýt baþarýyla silinmiþir.";
		}
	},
	NamePlaceHolder {
		public String toString() {
			return "Adýnýzý giriniz...";
		}
	},
	SurnamePlaceHolder {
		public String toString() {
			return "Soyadýnýzý giriniz...";
		}
	}
}