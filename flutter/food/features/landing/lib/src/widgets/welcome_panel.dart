import 'dart:convert';

import 'package:base/base.dart';
import 'package:flutter/material.dart';

class WelcomePanel extends StatelessWidget {
  const WelcomePanel({
    Key? key,
    this.country,
    this.onPhoneTextFieldTapped
  }) : super(key: key);

  final VoidCallback? onPhoneTextFieldTapped;

  final Country? country;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.fromLTRB(
        FoodSpacings.normal,
        FoodSpacings.xlarge,
        FoodSpacings.normal,
        FoodSpacings.xlarge
      ),
      color: FoodColors.surface,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          FoodText.h5(context.strings.landing_main_sheet_title),
          SizedBox(height: FoodSpacings.normal),
          GestureDetector(
            onTap: onPhoneTextFieldTapped,
            child: FoodTextField(
              isEditable: false,
              placeholder: context.strings.landing_main_sheet_hint,
              prefix: country?.code ?? '+',
              leadingIcon: country?.imageData != null
                ? Image.memory(base64.decode(country!.imageData), width: 32, height: 23)
                : Container(width: 32, height: 23, color: FoodColors.placeholder)
            )
          )
        ],
      ),
    );
  }
}
