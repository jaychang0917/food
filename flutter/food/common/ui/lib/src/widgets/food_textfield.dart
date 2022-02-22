
import 'package:flutter/material.dart';
import '../core/food_colors.dart';
import '../core/food_dimens.dart';
import '../core/food_text_styles.dart';
import '../widgets/food_text.dart';

class FoodTextField extends StatelessWidget {
  FoodTextField({
    this.controller,
    this.isEditable = true,
    this.placeholder,
    this.prefix,
    this.type = FoodTextFieldType.text,
    this.leadingIcon,
    this.onLeadingIconTapped,
    this.onChanged,
    this.autoFocus = true,
    this.color = FoodColors.background
  });

  final bool isEditable;

  final TextEditingController? controller;

  final String? placeholder;

  final String? prefix;

  final FoodTextFieldType type;

  final Widget? leadingIcon;

  final VoidCallback? onLeadingIconTapped;

  final ValueChanged<String>? onChanged;

  final bool autoFocus;

  final Color color;

  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: controller,
      style: FoodTextStyles.body2,
      enabled: isEditable,
      autofocus: autoFocus,
      obscureText: type == FoodTextFieldType.password,
      onChanged: onChanged,
      cursorColor: FoodColors.accent,
      keyboardType: _keyboardType() ,
      decoration: InputDecoration(
        contentPadding: const EdgeInsets.symmetric(horizontal: FoodSpacings.small),
        hintText: placeholder,
        filled: true,
        fillColor: color,
        border: _border,
        focusedBorder: _border,
        enabledBorder: _border,
        disabledBorder: _border,
        errorBorder: _border,
        prefixIcon: leadingIcon != null ? Padding(
          padding: const EdgeInsets.symmetric(horizontal: FoodSpacings.small),
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              GestureDetector(
                  child: Padding(
                    padding: const EdgeInsets.only(left: FoodSpacings.small),
                    child: leadingIcon,
                  ),
                  onTap: onLeadingIconTapped
              ),
              SizedBox(width: FoodSpacings.small),
              if (prefix != null)
                FoodText.body1(prefix!).withColor(FoodColors.onBackgroundPrimary)
            ],
          )
        ) : null
      ),
    );
  }

  final OutlineInputBorder _border = OutlineInputBorder(
      borderSide: BorderSide(color: FoodColors.transparent, width: 0),
      borderRadius: BorderRadius.circular(FoodCorners.small)
  );

  TextInputType _keyboardType() {
    if (type == FoodTextFieldType.phone) {
      return TextInputType.phone;
    } else {
      return TextInputType.text;
    }
  }
}

enum FoodTextFieldType {
  text, password, phone
}