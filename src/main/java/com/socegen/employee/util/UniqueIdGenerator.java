package com.socegen.employee.util;

import java.util.UUID;

public class UniqueIdGenerator {

	public String getUUID() {
		return Long.toString(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
	}

}
