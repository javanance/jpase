package com.eugenefe.model;

import com.eugenefe.enums.EKsdDataItem;

public class ElsHeader {

		private String isin;
		private int	seq;
		private String dataName;
		private EKsdDataItem dataItem;
		
		public ElsHeader(String isin, int seq, String dataName) {
			super();
			this.isin = isin;
			this.seq = seq;
			this.dataName = dataName;
		}

		public ElsHeader(String isin, int seq, EKsdDataItem dataItem) {
			super();
			this.isin = isin;
			this.seq = seq;
			this.dataItem = dataItem;
		}
		public String getIsin() {
			return isin;
		}

		public void setIsin(String isin) {
			this.isin = isin;
		}

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		public String getDataName() {
			return dataName;
		}

		public void setDataName(String dataName) {
			this.dataName = dataName;
		}
		
		public EKsdDataItem getDataItem() {
			return dataItem;
		}
		public void setDataItem(EKsdDataItem dataItem) {
			this.dataItem = dataItem;
		}
		
//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			result = prime * result	+ ((dataName == null) ? 0 : dataName.hashCode());
//			result = prime * result + ((isin == null) ? 0 : isin.hashCode());
//			result = prime * result + seq;
//			return result;
//		}
	//
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj)
//				return true;
//			if (obj == null)
//				return false;
//			if (getClass() != obj.getClass())
//				return false;
//			ElsHeader other = (ElsHeader) obj;
//			if (dataName == null) {
//				if (other.dataName != null)
//					return false;
//			} else if (!dataName.equals(other.dataName))
//				return false;
//			if (isin == null) {
//				if (other.isin != null)
//					return false;
//			} else if (!isin.equals(other.isin))
//				return false;
//			if (seq != other.seq)
//				return false;
//			return true;
//		}

		@Override
		public String toString() {
			String tempDataName = ( getDataItem() != null)  ?  dataItem.getLabel() : dataName  ;
			return "isin=" + isin + ",seq=" + seq + ",dataName="+ tempDataName ;

//			return "[isin=" + isin + ", seq=" + seq + ", dataName="+ dataName + "]";
//			return "isin=" + isin + ",seq=" + seq + ",dataName="+ dataName ;
//			return "isin=" + isin + ",seq=" + seq + ",dataName="+ dataItem.getLabel() ;
		}
		
}
