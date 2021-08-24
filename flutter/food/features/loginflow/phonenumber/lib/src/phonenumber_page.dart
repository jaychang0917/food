import 'dart:convert';

import 'package:base/base.dart';
import 'package:flutter/material.dart';

import 'phonenumber_bloc.dart';
import 'phonenumber_state.dart';
import 'phonenumber_event.dart';

class PhoneNumberPage extends StatefulWidget {
  const PhoneNumberPage({Key? key}) : super(key: key);

  @override
  _PhoneNumberPageState createState() => _PhoneNumberPageState();
}

class _PhoneNumberPageState extends State<PhoneNumberPage> {
  late TextEditingController _phoneNumberTextController;

  @override
  void initState() {
    super.initState();
    _phoneNumberTextController = TextEditingController();
  }

  @override
  void dispose() {
    _phoneNumberTextController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final loginFlowData = ModalRoute.of(context)!.settings.arguments;

    return BlocProvider(
      create: (context) {
        final countryRepository = RepositoryProvider.of<CountryRepository>(context);
        final userRepository = RepositoryProvider.of<UserRepository>(context);
        return PhoneNumberBloc(
          loginFlowData: loginFlowData,
          countryRepository: countryRepository,
          userRepository: userRepository
        )..add(Initialized());
      },
      child: BlocConsumer<PhoneNumberBloc, PhoneNumberState>(
        listener: (context, state) {
          if (state.navigation is PasswordNavigation) {
            Navigator.pushNamed(context, AppRoutes.password, arguments: state.loginFlowData);
          } else if (state.navigation is CountrySelectionNavigation) {
            Navigator.pushNamed(context, AppRoutes.countrySelection);
          }
        },
        builder: (context, state) {
          return Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              _header(context),
              if (state.isLoadingVisible) _loading(),
              _title(),
              _phoneInput(context, state),
              if (state.isErrorViewVisible) _error(),
              const Spacer(),
              if (state.isNextButtonVisible) _nextButton(context, state)
            ],
          );
        },
      ),
    );
  }

  Widget _header(BuildContext context) => FoodHeader.close(
    onNavigationIconTapped: () => Navigator.pop(context)
  );

  Widget _loading() => FoodLoading();

  Widget _title() => Padding(
    padding: const EdgeInsets.all(FoodSpacings.normal),
    child: FoodText.h6(context.strings.phonenumber_title)
  );

  Widget _phoneInput(BuildContext context, PhoneNumberState state) => Padding(
    padding: const EdgeInsets.symmetric(horizontal: FoodSpacings.normal),
    child: FoodTextField(
      controller: _phoneNumberTextController,
      onChanged: (text) => BlocProvider.of<PhoneNumberBloc>(context).add(PhoneTextChanged(text)),
      placeholder: context.strings.phonenumber_hint,
      type: FoodTextFieldType.phone,
      prefix: state.countryCode,
      leadingIcon: state.countryFlag != null
        ? Image.memory(base64.decode(state.countryFlag!), width: 32, height: 23)
        : Container(width: 32, height: 23, color: FoodColors.placeholder),
      onLeadingIconTapped: () async {
        final result = await Navigator.pushNamed(context, AppRoutes.countrySelection);
        if (result is Country) {
          BlocProvider.of<PhoneNumberBloc>(context).add(CountryUpdated(result));
        }
      },
    ),
  );

  Widget _error() => Padding(
    padding: const EdgeInsets.symmetric(horizontal: FoodSpacings.normal, vertical: FoodSpacings.medium),
    child: FoodText.body2(context.strings.phonenumber_error_invalid_phone).withColor(FoodColors.error)
  );

  Widget _nextButton(BuildContext context, PhoneNumberState state) => FoodButton(
    enabled: state.isNextButtonEnabled,
    text: context.strings.phonenumber_next,
    onTapped: () {
      BlocProvider.of<PhoneNumberBloc>(context).add(
        NextButtonClicked(_phoneNumberTextController.text)
      );
    }
  );
}
