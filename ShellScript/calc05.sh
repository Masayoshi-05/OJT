#!/bin/sh
#指定された複数のファイルから、テキスト検索を行う

inputLists=($@)

#指定されたファイル名が存在するか確認
listNum=0
for input in "${inputLists[@]}"
do
	if ! [ -e "$input" ]; then
		echo "$inputは見つかりませんでした"
		unset inputLists[$listNum]
	fi
	listNum=$(($listNum + 1))
done

#ファイル名が指定されているか確認
if [ ${#inputLists[@]} = 0 ]; then
	echo "ファイル名を指定してください"
	exit
fi

#検索ワードの入力
isCorrectSearchWord=false
while [ $isCorrectSearchWord = false ]
do
	echo "検索したい文字列を入力"
	read -r searchWord
	if ! [ -z "$searchWord" ]; then
		isCorrectSearchWord=true
		echo "検索対象のテキスト："$searchWord
	else
		isCorrectSearchWord=false
	fi
done

echo "検索結果"

hitCount=0
for file in "${inputLists[@]}"
do
	#指定されたファイルから日付け部分を除きテキスト部分のみを抽出
	targetText=`cat "$file" | sed -E 's/^.+\t//'`
	if echo "$targetText" | grep -F -q "$searchWord"; then
		echo "ファイル名：$file"
	fi

	lineNum=1
	while read -r line ;
	do
		#抽出したテキストが検索ワードを含むかどうか一行ずつ確認
		if echo "$line" | grep -F -q "$searchWord"; then
			#指定されたファイルからヒットしたテキストの存在する行を行数と併せて表示
			echo "$lineNum行目:`sed -n "$lineNum p" $file` "
			hitCount=$(($hitCount + 1))
		fi
		lineNum=$(($lineNum + 1))
	done < <(echo "$targetText")	
done

#検索ワードを含む行が１行も見つからなかった場合の処理
if [ $hitCount = 0 ]; then
	echo "条件に一致するファイルがありませんでした。"
fi

exit
