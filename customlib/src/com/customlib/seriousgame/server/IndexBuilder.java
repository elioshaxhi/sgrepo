/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.server;

import java.nio.file.Path;

/**
 *
 * @author gieze101
 */
public interface IndexBuilder {
	boolean build(Path destdir);
}
