package kr.pe.ihoney.jco.restapi.web.support.adapter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;

public class DateAdapter extends XmlAdapter<String, Date> {

	@Override
	public Date unmarshal(String v) throws Exception {
		return new DateTime(v).toDate();
	}

	@Override
	public String marshal(Date date) throws Exception {
		return date.toString();
	}

}
