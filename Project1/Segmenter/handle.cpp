#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <algorithm>
#include <sstream>
#include <string>

using namespace std;

int main()
{
	string input;
	
	istringstream is;
	while (cin >> input)
	{
		cout << input; //{"url"
		cin >> input;
		cout << input; //:
		cin >> input;
		cout << input; //[
		do
		{
			cin >> input;
			cout << input;
		} while (input != "]");
		cin >> input;
		cout << input; //,
		cin >> input;
		cout << input; //"
		cin >> input;
		cout << input; //content"
		cin >> input;
		cout << input; //:
		
		char line[100000];
		gets(line);
		//cerr << "+" << line << endl;
		while (!((line[0] == '"') && (line[1] == ' ') && (line[2] == ']')))
		{
			cout << line << "$";
			//cerr << line << "$";
			gets(line);
		}
		//cerr << "-" << line << "#" << endl;
		cout << line[0] << line[2] << line[4]; //"],
		for (int i = 6; i < 13; ++i)
			cout << line[i]; //"title"
		cout << line[14] << line[16] << line[18]; //:["
		//cerr << "#" << endl;
		int i = 20;
		for (; ; ++i)
		{
			if ((line[i] == ' ') && (line[i + 1] == '"'))
				break;
			cout << line[i];
		}
		//cerr << "#" << endl;
		int length = strlen(line);
		for (; i < length; ++i)
			if (line[i] != ' ')
				cout << line[i];
		
		//cerr << "#" << endl;
		gets(line);
		for (i = 0; i < 20; ++i)
			if (line[i] != ' ')
				cout << line[i];
				
		//cerr << "#" << endl;
		for (; line[i] != '"'; ++i)
			if (line[i] != ' ')
				cout << line[i];
		cout << "\"]}" << endl;
		//cerr << "#" << endl;
	}
}
