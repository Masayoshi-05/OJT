#!/bin/sh
#キーボードで入力した文字列をテキストファイルに出力する

#テキストファイルの作成
isDate=`date +'%Y%m%d_%H%M%S'`

if [ -e output_$isDate.tsv ]; then
	echo "テキストファイル" "output_$isDate.tsv" "は既に存在しています。"
else
	touch output_$isDate.tsv
	echo "テキストファイル" "output_$isDate.tsv" "を作成しました。"
fi

isLoop=true
while [ "$isLoop"  = "true" ]
do
	#テキストファイルへ文字の出力処理
	echo "テキストファイルに出力する文字を入力してください。"
	read -r text
	#タブ文字を区切り文字で使うため、改行を"\n"で表現するために-eオプションを使用するが、$textの中身はそのままで出力したいため、-nオプションを使用して、echoの処理を三行に分けている。
	echo -e -n `date +'%Y-%m-%d %H:%M:%S.%3N'` "\t" | iconv -t UTF-8 >>output_$isDate.tsv
	echo -n "$text" | iconv -t UTF-8 >>output_$isDate.tsv
	echo -e -n "\n" | iconv -t UTF-8 >>output_$isDate.tsv
	echo "出力が完了しました。"

	#処理を続けるかどうかの確認
	isCorrectInputContinue=false
	while [ "$isCorrectInputContinue" = "false" ]
	do
		echo ""
		echo "入力を続けますか？"
		echo "続ける→ Y"
		echo "やめる→ N"
		read "inputContinue"
		if [ "$inputContinue" = "Y" ] || [ "$inputContinue" = "y" ]; then
			isLoop=true
			isCorrectInputContinue=true
			echo ""
		elif [ "$inputContinue" = "N" ] || [ "$inputContinue" = "n" ]; then
			echo "入力を終了します"
			isLoop=false
			isCorrectInputContinue=true 
		else
			echo "YかNを入力してください。"
		fi
	done
done

exit
