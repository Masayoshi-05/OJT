#数値１、演算子、数値２を入力して四則演算を行い、結果を表示する

isLoop=true
while [ "$isLoop" = "true" ]
do
	#1つ目の整数の入力処理
	isCorrectInputNum1=false
	while [ "$isCorrectInputNum1" = "false" ]
	do
		echo "1つ目の整数を入力してください。"
		read "inputNum1"

		#インプットが整数か判定
		if [[ "$inputNum1" =~ ^[0-9]+$ ]]; then
			num1=$((10#$inputNum1))
			isCorrectInputNum1=true
		else
			echo "整数を入力してください。"
		fi	done
	
	#演算子の入力処理
	isCorrectInputOperator=false
	while [ "$isCorrectInputOperator" = "false" ]
	do
		echo "演算子(+,-,*,/)を入力してください。"
		read "inputOpertor"
		
		#インプットが演算子か判定
		if [ "$inputOperator" = "+" ] || [ "$inputOperator" = "-" ] || [ "$inputOperator" = "*" ] || [ "$inputOperator" = "/" ]; then
			operator="$inputOperator"
			isCorrectinputOperator=true
		else
			echo "四則演算(+,-,*,/)のみ使えます。"
		fi
	done

	#2つ目の整数の入力処理
	isCorrectInputNum2=false
	while [ "$isCorrectInputNum2" = "false" ]
	do
		echo "2つ目の整数を入力してください。"
		read "inputNum2"

		#インプットが整数か判定
		if [[ "$inputNum2" =~ ^[0-9]+$ ]]; then
			num2=$((10#$inputNum2))
			isCorrectInputNum2=true
		else
        		echo "整数を入力してください。"
		fi
	done

	#計算結果を表示
	echo ""
	echo "計算結果："

	#割り算の場合
	if [ "$operator" = "/" ]; then
		if [ "$num2" = "0" ]; then
			echo "0で割ることはできません"
		else
			result=$(("$num1" "$operator" "$num2"))
			echo "$num1" "$operator" "$num2 = $result"
			echo "余り：" "$(("$num1" - "$num2 * $result"))"
		fi

	#それ以外の場合
	else
		result=$(("$num1" "$operator" "$num2"))
		echo "$num1" "$operator" "$num2 = $result"
	fi
	
	#計算を続けるかどうか確認
	isCorrectInputContinue=false
	while [ "$isCorrectInputContinue" = "false" ]
	do
		echo "計算を続けますか？"
		echo "続ける→ Y、やめる→ N"
		read "inputContinue"
		if [ "$inputContinue" = "Y" ]; then
			isLoop=true
			isCorrectInputContinue=true
			echo ""
		elif [ "$inputContinue" = "N" ]; then
			isLoop=false
			isCorrectInputContinue=true 
		else
			echo "YかNを入力してください。"
		fi
	done
done
exit
