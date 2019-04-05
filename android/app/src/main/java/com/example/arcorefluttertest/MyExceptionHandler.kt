package com.example.arcorefluttertest

import android.content.Context
import android.content.Intent
import android.os.Process
import android.widget.Toast
import com.google.ar.core.exceptions.MissingGlContextException
import java.io.PrintWriter
import java.io.StringWriter


class MyExceptionHandler(private val myContext: Context, private val myActivityClass: Class<*>) : Thread.UncaughtExceptionHandler {

    //https://stackoverflow.com/a/47419148
    override fun uncaughtException(thread: Thread, exception: Throwable) {

        if (exception is MissingGlContextException) {
            val stackTrace = StringWriter()
            exception.printStackTrace(PrintWriter(stackTrace))
            System.err.println(stackTrace)// You can use LogCat too
            val intent = Intent(myContext, myActivityClass)
            val s = stackTrace.toString()
            //you can use this String to know what caused the exception and in which Activity
            intent.putExtra("uncaughtException",
                    "Exception is: " + stackTrace.toString())
            intent.putExtra("stacktrace", s)
            intent.putExtra(MainActivity.openArOnStartIdentifier, true)
            myContext.startActivity(intent)
            //for restarting the Activity
            Process.killProcess(Process.myPid())
            System.exit(0)
        }
    }
}