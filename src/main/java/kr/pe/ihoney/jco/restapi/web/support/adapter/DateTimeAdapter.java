package kr.pe.ihoney.jco.restapi.web.support.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;

public class DateTimeAdapter extends XmlAdapter<String, DateTime> {

    @Override
    public String marshal(DateTime v) throws Exception {
        return v.toString();
    }

    @Override
    public DateTime unmarshal(String v) throws Exception {
        return new DateTime(v);
    }

}
