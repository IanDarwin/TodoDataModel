package com.darwinsys.todo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import com.darwinsys.todo.model.Date;

/** Some tests of our custom Date class */
public class DateTest {

	private static final String DATE_STRING = "2013-06-01";
	Date d = new Date(2013,06,01); // Actually means June
	Date d0 = new Date(2013, 05, 25);
	Date d2 = new Date(2014, 01, 01);
	
	@Test
	public void testDateNoArg() {
		Date dx = new Date();
		final Calendar calInstance = Calendar.getInstance();
		assertEquals(dx.getYear(), calInstance.get(Calendar.YEAR));
		assertEquals(dx.getMonth(), calInstance.get(Calendar.MONTH) + 1);
		assertEquals(dx.getDay(), calInstance.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testDateIntIntInt() {
		assertEquals(2013, d.getYear());
		assertEquals(06, d.getMonth());
		assertEquals(01, d.getDay());
	}
	
	@Test
	public void testDateString() {
		Date dd = new Date(DATE_STRING);
		assertEquals(d, dd);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDateBadString() {
		Date dd = new Date(DATE_STRING.replace("06","June"));
		assertEquals(d, dd);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testDateFromJavaUtilDate() {
		java.util.Date jud = new java.util.Date();
		Date myDate = new Date(jud);
		assertTrue(myDate.getYear() == 1900 + jud.getYear());
		assertTrue(myDate.getMonth() == 1 + jud.getMonth());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetDate() {
		java.util.Date jud = d.asJULDate();
		assertEquals(2013, jud.getYear());
		assertEquals(6-1, jud.getMonth());
	}
	
	@Test
	public void testIsBefore() {
		assertTrue(d0.isBefore(d));
		assertTrue(d.isBefore(d2));
	}
	
	@Test
	public void testIsAfter() {
		assertTrue(d.isAfter(d0));
		assertTrue(d2.isAfter(d));
	}
	
	@Test
	public void testIsEqual() {
		assertTrue(d0.isEqual(d0));
		assertTrue(d.isEqual(d));
		assertTrue(d2.isEqual(d2));
		assertFalse(d.isEqual(d2));
	}

	@Test
	public void testToString() {
		assertEquals(DATE_STRING, d.toString());
	}
}
