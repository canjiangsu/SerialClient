// ISerialService.aidl
package com.t.serialserver;

// Declare any non-default types here with import statements
import com.t.serialserver.ISerialCallback;

interface ISerialService {
    int getPid();
    int openSerial(ISerialCallback cb);
}
