package com.wepiao.admin.user.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * ClassName:ServletOutputStreamCopier <br/>
 * Date: 2015年10月15日 下午7:11:05 <br/>
 *
 * @author Huapenpen
 */
public class ServletOutputStreamCopier extends ServletOutputStream {

    private OutputStream          outputStream;
    private ByteArrayOutputStream copy;

    public ServletOutputStreamCopier(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(256);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }

    @Override
    public boolean isReady() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        // TODO Auto-generated method stub

    }

}
