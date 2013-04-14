package org.cevh.ck2;

import java.nio.file.Path;

import org.cevh.domain.ck2.CK2Save;

public interface CK2SaveLoader {

	public CK2Save load(Path path);
}
