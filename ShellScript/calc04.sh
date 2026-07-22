#!/bin/ish
#指定されたファイルから、テキスト検索を行う

#ファイル名が指定されているか確認
if [ -z $1 ]; then
	echo "ファイル名を指定してください"
	exit
fi

#指定されたファイル名が存在するか確認
if [ -e $1 ]; then
	inputFile="$1"
else
	echo "$1は見つかりませんでした"
	exit
fi

#検索ワードの入力
isCorrectSearchText=false
while [ $isCorrectSearchText = false ]
do
	echo "検索したい文字列を入力"
	read -r searchText
	if ! [ -z "$searchText" ]; then
		isCorrectSearchText=true
	else
		isCorrectSearchText=false
	fi
done

echo "検索対象のテキスト："$searchText

#指定されたファイルから日付け部分を除きテキスト部分のみを抽出
targetText=`cat "$inputFile" | sed -E 's/^.+\t//'`

lineNum=1
hitCount=0
while read -r line ;
do
	#抽出したテキストが検索ワードを含むかどうか一行ずつ確認
	if echo "$line" | grep -F -q "$searchText"; then
		#指定されたファイルからヒットしたテキストの存在する行を行数と併せて表示
		echo "$lineNum行目:`sed -n "$lineNum p" $inputFile` "
		hitCount=$(($hitCount + 1))
	fi
	lineNum=$(($lineNum + 1))
done < <(echo "$targetText")

#検索ワードを含む行が１行も見つからなかった場合の処理
if [ $hitCount = 0 ]; then
	echo "条件に一致する行がありませんでした。"
fi

exit
