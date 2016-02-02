<HTML>
<HEAD>
<TITLE>查看PHP的环境变量</TITLE>
</HEAD>
<BODY>
<?
    print("你正在用文件的名字为： ");
    print(__FILE__);
    print(" <BR>\n");
    print("<hr>");
    print("你的操作系统为： ");
    print(PHP_OS);
    print("<hr>");
    print("你的php的版本为： ");
    print(PHP_VERSION)
?>
</BODY>
</HTML>