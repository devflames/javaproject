package common;



public class EmojiTable {

	public static String convert(String src) throws Exception {

		StringBuffer dst = new StringBuffer(src.length());	// 変換後の文字列
		StringBuffer nstr = new StringBuffer();				// 変換中の絵文字番号

		int index_prev = 0;		// １つ前の検索位置
		int index;				// 現在の検索位置
		boolean end = false;

		while ( !end ) {

			index = src.indexOf("#{", index_prev);

			if ( index == -1 ) {
				dst.append(src.substring(index_prev));
				end = true;

			} else {

				dst.append(src.substring(index_prev, index));
				nstr.delete(0, nstr.length());

				for ( int i = index + 2; ; i++ ) {

					if ( i >= src.length() ) {
						dst.append(src.substring(index));
						end = true;
						break;

					} else {
						char n = src.charAt(i);

						if ( n == '}' ) {
							if ( nstr.length() > 0 ) {
								try {
									dst.append( getCode( nstr.toString() ));

								} catch (Exception ex) {}

							} else {
								dst.append(src.substring(index, i + 1));
							}

							if ( i + 1 >= src.length() ) {
								end = true;
							} else {
								index_prev = i + 1;
							}
							break;

						} else {
							nstr.append(n);
						}
					}
				}
			}
		}
		return dst.toString();
	}


	// 絵文字コードを取得する。
	public static String getCode(String cd ) throws Exception {

		String code = "";

		byte b[];
		b = new byte[4];

		b[0] = 0;
		b[1] = (byte)Integer.parseInt( cd.substring(0, 2), 16);
		b[2] = (byte)Integer.parseInt( cd.substring(2, 4), 16);
		b[3] = (byte)Integer.parseInt( cd.substring(4, 6), 16);

		code = new String(b,"UTF-32");

		return code;
	}
}
