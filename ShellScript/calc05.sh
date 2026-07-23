#!/bin/sh
#指定された複数のファイルから、テキスト検索を行う

inputList=($@)
targetFiles=()

#ファイル名が指定されているか確認
if [ ${#inputList[@]} = 0 ]; then
        echo "ファイル名を指定してください"
        exit
fi

#指定されたファイル名が存在するか確認
listNum=0
for input in "${inputList[@]}"
do
	if [ -e "$input" ]; then
		targetFiles+=("$input")
	else
		echo "$inputは見つかりませんでした"
	fi
	listNum=$(($listNum + 1))
done

#存在するファイルが一つも指定されていなかった場合の処理
if [ ${#targetFiles[@]} = 0 ]; then
	exit
fi

#検索条件の入力
isCorrectSearchText=false
while [ $isCorrectSearchText = false ]
do
	echo "検索したい文字列を入力"
	read -r searchText
	if ! [ -z "$searchText" ]; then
		isCorrectSearchText=true
		echo "検索対象のテキスト："$searchText
	else
		isCorrectSearchText=false
	fi
done

echo "検索結果"

hitCount=0
for file in "${targetFiles[@]}"
do
	#指定されたファイルから日付け部分を除きテキスト部分のみを抽出
	excludeDateLines=`cat $file | sed -E 's/^.+\t//'`

	#ファイルのテキスト部に検索条件が含まれるか確認し、なければ次のファイルへ
	if echo "$excludeDateLines" | grep -F -q "$searchText"; then
		echo "ファイル名：$file"
	else
		continue
	fi

	lineNum=1
	while read -r line ;
	do
		IFS=$'\t' read -r -a separatedLine < <(echo "$line")
		writeDate=${separatedLine[0]}
		targetText=${separatedLine[1]}

		#抽出したテキストが検索ワードを含むかどうか一行ずつ確認
		if echo "$targetText" | grep -F -q "$searchText"; then
			#ヒットしたテキストを書き込み日・行数と併せて表示
			echo "$lineNum行目:$writeDate $targetText"
			hitCount=$(($hitCount + 1))
		fi
		lineNum=$(($lineNum + 1))
	done < "$file"
done

#検索ワードを含む行が１行も見つからなかった場合の処理
if [ $hitCount = 0 ]; then
	echo "条件に一致するファイルがありませんでした。"
fi

exit
