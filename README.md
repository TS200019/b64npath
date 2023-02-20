# Teradata Vantageのnpath関数でUNICODEに対応するためのbase64関数作成プロジェクト
このプロジェクトは、Teradata UDF でBase64 のエンコードとデコードを行うため関数作成のためのプロジェクトです。  
この関数は次の機能があります。
- String b64enc(String)
　指定された文字列をエンコード文字列に置き換えます。
- String b64dec(String)
　指定されたエンコード文字列を文字列に置き換えます。
- Stroing b64decary(string)
　指定されたエンコード文字列配列を文字列配列に置き換えます。
　配列のイメージ) [xxx, yyy, zzz] -> [aaa, bbb, ccc] 

## (1)Jarfileを作成する
Javaソースプログラムをコンパイルして任意のフォルダにjarファイルをエクスポートします。  
こちらの例では、"C:\temp"の下にjarファイルを出力しています。  
最新のコンパイル済みJarファイルは、**本プロジェクト内のDistフォルダ**にあります。

	C:/temp/B64npath.jar

## (2)Teradataのdbcにログインします

	.logon dbc,{パスワード}

## (3)UDF権限を付与
	GRANT EXECUTE PROCEDURE ON sqlj to {ターゲットユーザ};
	GRANT FUNCTION  ON {ターゲットユーザ} TO {ターゲットユーザ};

## (4)TeradataのUDFを実行するユーザにログインします

	.logon {ターゲットユーザ},{パスワード}

## (5)JarfileをDBからリムーブする。(6)未実施の場合はスキップしてください。
	CALL SQLJ.REMOVE_JAR('B64npath', 0); 

## (6)JarfileをDBにインポートする

	CALL SQLJ.INSTALL_JAR('CJ!C:/temp/B64npath.jar', 'b64npath', 0); 

## (7)UDF関数を定義する

	replace FUNCTION b64enc(p1 varchar(1024) Character set unicode)
	RETURNS varchar(1024) character set unicode
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'b64npath:com.teradata.b64npath.enc(java.lang.String) returns java.lang.String';

	replace FUNCTION b64dec(p1 varchar(1024) character set unicode)
	RETURNS varchar(1024) character set unicode
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'b64npath:com.teradata.b64npath.dec(java.lang.String) returns java.lang.String';

	replace FUNCTION b64decary(p1 varchar(1024) character set unicode)
	RETURNS varchar(1024) character set unicode
	LANGUAGE JAVA
	NO SQL
	PARAMETER STYLE JAVA
	RETURNS NULL ON NULL INPUT
	EXTERNAL NAME 'b64npath:com.teradata.b64npath.decary(java.lang.String) returns java.lang.String';

## (8)UDF関数の実行方法。
### (8.1)Base64文字列にエンコードします
	select b64enc({文字列});

### (8.2)Base64文字列をデコードします
	select b64dec({エンコード文字列});

### (8.3)Base64文字列配列をデコードします
	select b64decary({エンコード文字列配列});
